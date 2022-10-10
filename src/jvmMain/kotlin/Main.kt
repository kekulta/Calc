import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application



fun main() = application {

    System.setProperty("skija.logLevel", "DEBUG")
    Window(
        onCloseRequest = ::exitApplication,
        title = "Calc",
        state = WindowState(width = 1280.dp, height = 768.dp),
        icon = BitmapPainter(useResource("ic_launcher.png", ::loadImageBitmap))
    ) {
        MainView()
    }
}
