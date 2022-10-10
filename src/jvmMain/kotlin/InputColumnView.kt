import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable

@Composable
fun InputColumnView(model: Model) {
    LazyColumn{
        items(model.parameters.size) {
            InputItemView(model.parameters[it])
        }
    }
}