import kotlin.math.cos
import kotlin.math.sin
import org.openrndr.application
import org.openrndr.color.ColorRGBa

fun main() = application {
    configure {
        width = 800
        height = 600
    }

    program {
        val particleCount = 8
        val orbitRadius = 150.0
        val particleRadius = 16.0

        val backgroundColor = ColorRGBa.fromHex("#0F1420")
        val centerColor = ColorRGBa.fromHex("#FF78B4")
        val particleColor = ColorRGBa.fromHex("#64DCFF")

        extend {
            drawer.background(backgroundColor)
            drawer.stroke = null

            val centerX = mouse.position.x
            val centerY = mouse.position.y
            val time = seconds

            // 중앙 원
            drawer.fill = centerColor
            drawer.circle(
                centerX,
                centerY,
                50.0
            )

            // 공전하는 작은 원
            for (i in 0 until particleCount) {
                val angle =
                    time +
                            i * Math.PI * 2.0 / particleCount

                val x =
                    centerX +
                            cos(angle) * orbitRadius

                val y =
                    centerY +
                            sin(angle) * orbitRadius

                drawer.fill = particleColor
                drawer.circle(
                    x,
                    y,
                    particleRadius
                )
            }
        }
    }
}