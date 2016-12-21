package ru.spbau.mit.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.icons.FontAwesome
import com.karasiq.bootstrap.navbar.{NavigationBar, NavigationBarStyle, NavigationTab}
import org.scalajs.dom
import org.scalajs.jquery._
import rx._

import scala.language.postfixOps
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

/**
  * Created by edgar on 19.12.16.
  */
object Example extends JSApp {
  private implicit val context = implicitly[Ctx.Owner] // Stops working if moved to main(), macro magic

  @JSExport
  override def main(): Unit = {
    jQuery(() ⇒ {
      // Table tab will appear after 3 seconds
      val navigationBar = NavigationBar()
        .withBrand("Scala.js Bootstrap Test", href := "http://getbootstrap.com/components/#navbar")
        .withTabs(
          NavigationTab("Main", "table", "table".fontAwesome(FontAwesome.fixedWidth), new Comparer)
        )
        .withContentContainer(content ⇒ GridSystem.container(id := "main-container", GridSystem.mkRow(content)))
        .withStyles(NavigationBarStyle.inverse, NavigationBarStyle.staticTop)
        .build()

      // Render page
      navigationBar.applyTo(dom.document.body)
    })
  }
}
