import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import kotlin.math.*

enum class State {
    POLAR, OFFSET, ABSOLUTE, NO_FOCUS
}


data class InputModel(
    var length: MutableState<String> = mutableStateOf("0"),
    var angle: MutableState<String> = mutableStateOf("0"),
    var xOffset: MutableState<String> = mutableStateOf("0"),
    var yOffset: MutableState<String> = mutableStateOf("0"),
    var absoluteXOffset: MutableState<String> = mutableStateOf("0"),
    var absoluteYOffset: MutableState<String> = mutableStateOf("0"),
    var state: MutableState<State> = mutableStateOf(State.OFFSET)
)


fun areaByVerticesOrNull(vertices: List<Offset>): Float? {
    if (vertices.size < 3) return null
    var sum = 0f
    for (i in 0 until vertices.size - 1) {
        val step =
            (vertices[i].x * vertices[i + 1].y - vertices[i + 1].x * vertices[i].y) - (vertices[0].x * vertices[vertices.lastIndex].y - vertices[vertices.lastIndex].x * vertices[0].y)
        sum += step
    }
    sum /= 2
    sum = sum.absoluteValue
    return sum
}

fun verticesFromLines(lines: List<Line>): List<Offset> {
    if (lines.isEmpty()) return listOf(Offset(0f, 0f))
    val vertices = mutableListOf<Offset>()
    lines.forEach {
        vertices.add(it.start)
    }
    vertices.add(lines.last().end)
    return vertices.toList()
}

fun linesFromVertices(vertices: List<Offset>): List<Line> {
    val lines = mutableListOf<Line>()
    if (vertices.size < 2) return lines.toList()
    for (i in 1 .. vertices.lastIndex) {
        lines.add(Line(start = vertices[i-1], end = vertices[i]))
    }
    return lines.toList()
}

fun PolarOffset(length: Float, angle: Float): Offset {
    return Offset(
        length * cos(Math.toRadians(angle.toDouble())).toFloat(),
        length * sin(Math.toRadians(angle.toDouble())).toFloat()
    )
}


