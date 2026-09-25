import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector2
import kotlin.random.Random

// 원 데이터를 담는 데이터 클래스
data class CloudCircle(
    var position: Vector2,
    var velocity: Vector2,
    val radius: Double,
    val seed: Float
)

fun main() = application {
    configure {
        width = 800
        height = 800
    }

    program {
        // 1. 랜덤 크기와 위치의 원 10개 생성
        val circles = List(10) {
            CloudCircle(
                position = Vector2(Random.nextDouble(100.0, 700.0), Random.nextDouble(100.0, 700.0)),
                velocity = Vector2(Random.nextDouble(-1.0, 1.0), Random.nextDouble(-1.0, 1.0)),
                radius = Random.nextDouble(40.0, 100.0),
                seed = Random.nextFloat() * 100.0f
            )
        }

        // 2. 구름 노이즈 셰이더 정의 (shadeStyle)
        val cloudStyle = shadeStyle {
            parameter("time", 0.0)
            parameter("seed", 0.0f)

            // GLSL 의사 노이즈 함수 및 구름 합성 로직
            fragmentTransform = """
                vec2 st = c_boundsPosition.xy;
                
                // 간단한 2D 노이즈 함수
                vec2 coord = st * 4.0 + vec2(p_time * 0.2 + p_seed);
                float n = sin(coord.x) * cos(coord.y) * 0.5 + 0.5;
                
                // 중심으로부터의 거리 계산 (원형 마스킹 및 페더링)
                float dist = length(st - vec2(0.5));
                float mask = smoothstep(0.5, 0.4, dist); // 원 테두리 부드럽게
                
                // 구름 색상 (흰색/연회색 구름과 하늘색 배경 blend)
                vec3 cloudColor = mix(vec3(0.6, 0.7, 0.9), vec3(1.0, 1.0, 1.0), n);
                
                x_fill = vec4(cloudColor, mask);
            """.trimIndent()
        }

        extend {
            // 흰색 배경
            drawer.clear(ColorRGBa.WHITE)

            // 원 업데이트 및 그리기
            for (circle in circles) {
                // 1) 위치 이동 (화면 밖으로 나가면 반사)
                circle.position += circle.velocity
                if (circle.position.x < circle.radius || circle.position.x > width - circle.radius) circle.velocity = Vector2(-circle.velocity.x, circle.velocity.y)
                if (circle.position.y < circle.radius || circle.position.y > height - circle.radius) circle.velocity = Vector2(circle.velocity.x, -circle.velocity.y)

                // 2) 셰이더 유니폼 파라미터 전달
                cloudStyle.parameter("time", seconds)
                cloudStyle.parameter("seed", circle.seed)

                // 3) 셰이더 적용 후 원 그리기
                drawer.shadeStyle = cloudStyle
                drawer.stroke = null // 외각선 제거
                drawer.circle(circle.position, circle.radius)
            }

            // 셰이더 초기화
            drawer.shadeStyle = null
        }
    }
}