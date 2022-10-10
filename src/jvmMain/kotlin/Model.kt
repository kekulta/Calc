import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset

class Model {
    val parameters = mutableStateListOf<Parameter>()

    val lines
    get() = linesFromVertices(_vertices)

    val vertices
    get() = _vertices.toList()

    private val _vertices = mutableStateListOf(Offset(0f, 0f))



    fun deleteVertex(vertex: Offset) {
        if (_vertices.size == 1) return
        _vertices.removeAt(_vertices.indexOf(vertex))

    }


    fun addVertexByOffset(offset:Offset) {
        this.addVertexByPoint((this._vertices.lastOrNull() ?: Offset(0f, 0f)) + offset)
    }

    fun addVertexByPoint(point: Offset) {
        this._vertices.add(point)
    }


    fun addParameter(
        name: String = "NAME",
        value: MutableState<String> = mutableStateOf("0"),
        isDigitValue: Boolean = true,
        row: String = "1",
        column: String = "A",
    ) {
        parameters.add(Parameter(name = name, value = value, isDigitValue = isDigitValue, row = row, column = column))
    }
}