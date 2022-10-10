import androidx.compose.ui.geometry.Offset
import java.lang.Float.max
import java.lang.Float.min
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

class Line (val start: Offset, val end: Offset) {


    fun distanceFromPoint(point: Offset): Float{
        return ((this.end.y-this.start.y)*point.x-(this.end.x-this.start.x)*point.y+this.end.x*this.start.y-this.end.y*this.start.x).absoluteValue/(sqrt((this.end.y-this.start.y).pow(2f)+(this.end.x-this.start.x).pow(2f)))
    }

    fun safeContains(point: Offset, wide: Float): Boolean {
        //Return true if this Line contains given point except start point of Line

        return this.contains(point = point, wide = wide)
                && !(point.x in start.x - wide/2 .. start.x + wide/2
                && point.y in start.y - wide/2 .. start.y + wide/2)
    }

    fun contains(point: Offset, wide: Float): Boolean {
        return point.x in min(start.x, end.x) - wide .. max(start.x, end.x) + wide/2
                && point.y in min(start.y, end.y) .. max(start.y, end.y) + wide/2
                && distanceFromPoint(point) <= wide/2

    }

    override fun toString(): String {
        return "Line($start, $end)"
    }
}

