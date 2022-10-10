import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun MainView() {
    val model = remember {
        Model().apply {
            addParameter(name = "Length")
            addParameter(name = "Width", row = "2", column = "A")
            addParameter(row = "1", column = "B")
            addParameter(row = "2", column = "B")
        }
    }

    DisableSelection {
        MaterialTheme {
            Surface {
                InputView(model)
            }
        }
    }
}