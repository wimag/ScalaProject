package ru.spbau.mit.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.buttons.{Button, ButtonStyle}
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.form._
import com.karasiq.bootstrap.{Bootstrap, BootstrapComponent}
import rx._

import scalatags.JsDom.all._

final class Comparer(implicit ctx: Ctx.Owner) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = {
    val firstText = Var("First word")
    val secondText = Var("First word")
    val firstWordInput = FormInput.text(placeholder := "First word", firstText.reactiveInput).render
    val secondWordInput = FormInput.text(placeholder := "Second word", secondText.reactiveInput).render
    val successButton = Button(ButtonStyle.success)("Win 10000000$", onclick := Bootstrap.jsClick(_ => println(firstText.now + " " + secondText.now))).render
    // Render table
    val res = GridSystem
    Form(
      firstWordInput,
      secondWordInput,
      successButton
    )
  }

}