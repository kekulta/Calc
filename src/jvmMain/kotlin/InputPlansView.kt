import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.jetbrains.skia.impl.Log
import kotlin.math.pow
import kotlin.math.sqrt

val input = InputModel()

@Composable
fun InputTextField(value: MutableState<String>, state: State, text: String) {
    OutlinedTextField(
        value = value.value,
        onValueChange = {
            if (it.all { char -> char.isDigit() || char == '-' }) value.value = it
        },
        label = { Text(text) },
        modifier = Modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) input.state.value = state
            else input.state.value = State.NO_FOCUS
        }
    )
}

fun InputModel.addPreviewLine(model: Model) {
    when (this.state.value) {
        State.POLAR -> {
            model.addVertexByOffset(this.polarOffset())
        }
        State.OFFSET -> {
            model.addVertexByOffset(this.offset())
        }
        State.ABSOLUTE -> {
            model.addVertexByPoint(this.absoluteOffset())
        }
        State.NO_FOCUS -> {
            //Do nothing
        }
    }
}

fun InputModel.absoluteOffset(): Offset {
    return Offset(absoluteXOffset.value.toFloatOrNull() ?: 0f, absoluteYOffset.value.toFloatOrNull() ?: 0f)
}

fun InputModel.polarOffset(): Offset {
    return PolarOffset(length.value.toFloatOrNull() ?: 0f, angle.value.toFloatOrNull() ?: 0f)
}

fun InputModel.offset(): Offset {
    return Offset(xOffset.value.toFloatOrNull() ?: 0f, yOffset.value.toFloatOrNull() ?: 0f)
}

fun InputModel.getPreviewLine(vertices: List<Offset>): Line {
    val start = vertices.lastOrNull() ?: Offset(0f, 0f)

    val end = when (this.state.value) {
        State.POLAR -> {
            start + this.polarOffset()
        }
        State.OFFSET -> {
            start + this.offset()
        }
        State.ABSOLUTE -> {
            this.absoluteOffset()
        }
        State.NO_FOCUS -> {
            start
        }
    }

    return Line(start, end)
}


fun DrawScope.drawPreview(line: Line) {
    this.drawLine(
        start = line.start,
        end = line.end,
        color = Color.Cyan,
        strokeWidth = 16f,
        cap = StrokeCap.Round
    )
}


fun DrawScope.drawLines(lines: List<Line>) {
    lines.forEach {
        this.drawLine(
            start = it.start,
            end = it.end,
            color = Color.Blue,
            strokeWidth = 16f,
            cap = StrokeCap.Round,

            )
    }
}

fun DrawScope.drawVertices(vertices: List<Offset>) {
    this.drawPoints(
        points = vertices,
        pointMode = PointMode.Points,
        color = Color.Gray,
        strokeWidth = 17f,
        cap = StrokeCap.Round
    )
}

fun Offset.distance(point: Offset): Float {
    return sqrt((this.x - point.x).pow(2) + (this.y - point.y).pow(2))
}

@Composable
fun InputPlansView(model: Model) {
    Column {
        Canvas(
            modifier = Modifier.border(width = 2.dp, color = Color.Black, shape = AbsoluteRoundedCornerShape(10.dp))
                .size(500.dp)
                .clipToBounds()
                .padding(10.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        val tap  = it
                        var flag = true
                        for(vertex in model.vertices) {
                            if (tap.distance(point = vertex) <= 17) {
                                model.deleteVertex(vertex = vertex)
                                flag = false
                                break
                            }
                        }
                        if (flag) {
                            if (input.getPreviewLine(vertices = model.vertices).contains(it, 16f)) {
                                input.addPreviewLine(model = model)
                            }
                        }
                    }
                }

        ) {
            drawLines(model.lines)
            drawPreview(input.getPreviewLine(model.vertices))
            drawVertices(model.vertices)
        }
        Text("Area: ${areaByVerticesOrNull(model.vertices)?.toString() ?: "0"}")

        Row(modifier = Modifier.size(500.dp)) {
            Column(modifier = Modifier.weight(weight = 1f)) {
                InputTextField(value = input.length, State.POLAR, "Length")
                InputTextField(value = input.angle, State.POLAR, "Angle")
                Button(onClick = {
                    input.state.value = State.POLAR
                    model.addVertexByOffset(input.polarOffset())
                }) {
                    Text("Draw By Polar")
                }
            }
            Column(modifier = Modifier.weight(weight = 1f)) {
                InputTextField(value = input.xOffset, State.OFFSET, "X Offset")
                InputTextField(value = input.yOffset, State.OFFSET, "Y Offset")

                Button(onClick = {
                    input.state.value = State.OFFSET
                    model.addVertexByOffset(input.offset())
                }) {
                    Text("Draw By Offset")
                }
            }
            Column(modifier = Modifier.weight(weight = 1f)) {
                InputTextField(value = input.absoluteXOffset, State.ABSOLUTE, "X Abs. Offset")
                InputTextField(value = input.absoluteYOffset, State.ABSOLUTE, "Y Abs. Offset")

                Button(onClick = {
                    input.state.value = State.ABSOLUTE
                    model.addVertexByPoint(input.absoluteOffset())
                }) {
                    Text("Draw By Abs. Offset")
                }
            }
        }

    }
}


