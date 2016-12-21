package ru.spbau.mit.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.buttons.{Button, ButtonStyle}
import com.karasiq.bootstrap.carousel.Carousel
import com.karasiq.bootstrap.form._
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.{Bootstrap, BootstrapComponent}
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

final class Comparer(implicit ctx: Ctx.Owner) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = {
    val firstText = Var("First word")
    val secondText = Var("Second word")
    val picText = Var("")
    val firstWordInput = FormInput.text(placeholder := "First word", firstText.reactiveInput).render
    val secondWordInput = FormInput.text(placeholder := "Second word", secondText.reactiveInput).render

    val successButton = Button(ButtonStyle.success)("Plot", onclick.:=(Bootstrap.jsClick(_ => {
      val subj1 = firstText.now
      val subj2 = secondText.now
      val now = System.currentTimeMillis / 1000 // in seconds
      val from = now - 300 // five minutes ago

      val url = s"http://127.0.0.1:9000/stats?first=$subj1&second=$subj2&from=$from&to=$now"

      val xhr = new dom.XMLHttpRequest()
      xhr.open("GET", url)
      xhr.onload = { (e: dom.Event) =>
        if (xhr.status == 200) {
          val text = xhr.responseText
          picText.update(text)
          println(picText.now)
        }
      }
      xhr.send()
    }))).render

    val pic = GridSystem.mkRow(Carousel.slide(picText.now)).render
    // Render table

    val res = Form(
      firstWordInput,
      secondWordInput,
      successButton,
      pic
    )
    res
  }

}