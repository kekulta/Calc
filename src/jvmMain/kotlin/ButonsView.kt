import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ButtonsView(model: Model) {
    Row {
        Button(onClick = { exportToExcel(model) }) {
            Text("Export to Excel")
        }
        Button(onClick = {model.addParameter(name = "Length")}) {
            Text("New param")
        }
    }
}

