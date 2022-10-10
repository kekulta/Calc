import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable



@Composable
fun InputView(model: Model) {
    Row {
        Column {
            InputColumnView(model)
            ButtonsView(model)
        }
        InputPlansView(model)
    }
}
