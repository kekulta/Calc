import androidx.compose.runtime.MutableState

class Parameter(
    val name: String,
    var value: MutableState<String>,
    val isDigitValue: Boolean,
    val row: String,
    val column: String,
)