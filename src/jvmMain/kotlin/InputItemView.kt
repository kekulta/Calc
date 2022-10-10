import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun InputItemView(parameter: Parameter) = Column {
    OutlinedTextField(value = parameter.value.value,
        onValueChange = { if (it.all { char -> char.isDigit() } || !parameter.isDigitValue) parameter.value.value = it},
        label = { Text(parameter.name) }
    )
}