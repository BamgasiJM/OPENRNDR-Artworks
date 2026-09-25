// imports are added automatically by the IDE
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.rgb
import org.openrndr.draw.font.loadFace
import org.openrndr.draw.loadFont
import org.openrndr.shape.Rectangle
import org.openrndr.writer
import kotlin.math.cos
import kotlin.math.sin

fun main() = application {
    configure {                              // -- drawing code goes here
        width = 1080
        height = 1080
        windowResizable = false
        title = "OPENRNDR Study"
    }
    program {
        // -- what is here executed ONCE
        val font = loadFont("data/fonts/Gilroy_SemiBold.otf", 55.0)

        extend {
            // -- drawing code goes here. what is here executed 'as often as possible'
            drawer.clear(0.4, 0.7, 0.7, 1.0)
            drawer.fontMap = font
            drawer.fill = ColorRGBa(1.0, 1.0, 1.0)
            drawer.stroke = null
            drawer.circle(mouse.position, width / 4.0)

            writer {
                //----animate the text leading
                leading = cos(seconds) * 10.0 + 24.0
                //----animate the text tracking

                box = Rectangle(40.0, 40.0, 600.0, 1080.0)
                newLine()
                text("To load the vector data of a font file")
                newLine()
                text("use the loadFace() method, then call the .glyphForCharacter() method")
                newLine()
                text("to obtain a Shape representing a character.")
            }
        }
    }
}
