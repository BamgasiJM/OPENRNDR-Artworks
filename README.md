# OPENRNDR 프로젝트 시작 가이드

OPENRNDR을 macOS VSCode에서 시작하기 위한 완벽한 가이드입니다.

## 📋 목차
1. [필수 요구사항](#필수-요구사항)
2. [프로젝트 구조](#프로젝트-구조)
3. [초기 설정](#초기-설정)
4. [코드 작성 및 실행](#코드-작성-및-실행)
5. [빌드 설정 변경](#빌드-설정-변경)

---

## 필수 요구사항

### 설치해야 할 것
- **Java Development Kit (JDK)** - 11 이상
  - macOS 기본 설정에서는 Corretto JDK 22 추천
  - 설치 확인: `java -version`

- **Gradle** - 프로젝트에 포함됨 (별도 설치 불필요)
  - Gradle Wrapper(`./gradlew`)를 통해 자동 관리

- **VSCode**
  - 추천 확장:
    - Kotlin Language
    - Gradle for Java (optional)

---

## 프로젝트 구조

```
OPENRNDR/
├── Practice/                      # 모든 작업이 진행되는 메인 폴더
│   ├── src/
│   │   └── main/
│   │       └── kotlin/            # Kotlin 소스 코드
│   │           ├── Test1017.kt
│   │           ├── Test1025.kt
│   │           └── ...
│   ├── data/                      # 리소스 파일
│   │   ├── fonts/
│   │   ├── images/
│   │   └── shaders/
│   ├── build.gradle.kts           # 빌드 설정 (메인 클래스 지정)
│   ├── settings.gradle.kts
│   ├── gradle/                    # Gradle 설정
│   └── gradlew / gradlew.bat      # Gradle Wrapper
│
└── openrndr-master/               # OPENRNDR 라이브러리 (참고용)
```

### 폴더별 역할

| 폴더 | 설명 |
|------|------|
| `Practice/` | 모든 개인 프로젝트 작업 폴더 |
| `src/main/kotlin/` | 작성한 Kotlin 파일들 (`.kt`) |
| `data/` | 폰트, 이미지, 쉐이더 등 리소스 |
| `build.gradle.kts` | 빌드 설정 및 메인 클래스 지정 |

---

## 초기 설정

### 1. Gradle Wrapper 권한 설정

처음 한 번만 실행하면 됩니다:

```bash
cd /Users/jaikim/Documents/CodingStudy/OPENRNDR/Practice
chmod +x gradlew
```

### 2. 프로젝트 빌드 테스트

```bash
./gradlew build
```

첫 빌드는 모든 의존성을 다운로드하므로 1-2분 소요됩니다.

---

## 코드 작성 및 실행

### 기본 Kotlin 구조

`src/main/kotlin/` 폴더에 새로운 `.kt` 파일을 만듭니다:

```kotlin
import org.openrndr.application
import org.openrndr.color.ColorRGBa

fun main() = application {
    configure {
        width = 1080
        height = 1080
        title = "My OPENRNDR Project"
    }
    program {
        extend {
            drawer.clear(ColorRGBa.BLACK)
            drawer.fill = ColorRGBa.WHITE
            drawer.circle(mouse.position, 50.0)
        }
    }
}
```

### 파일 실행하기

#### 단계 1: build.gradle.kts 수정

`Practice/build.gradle.kts` 9번 라인을 편집합니다:

```gradle
val applicationMainClass = "Test1025Kt"  // 실행할 파일명 + "Kt"
```

**규칙**:
- Kotlin 파일명: `Test1025.kt`
- Main class 이름: `Test1025Kt` (파일명 + "Kt" 접미사)

예시:
- `MySketch.kt` → `"MySketchKt"`
- `TemplateProgram.kt` → `"TemplateProgramKt"`

#### 단계 2: 터미널에서 실행

```bash
cd /Users/jaikim/Documents/CodingStudy/OPENRNDR/Practice
./gradlew run
```

또는 VSCode에서:
1. `Ctrl+`` (또는 `Cmd+``) 터미널 열기
2. 위 명령 실행

#### 단계 3: 창 닫기

창을 닫으면 프로그램 종료됩니다.

---

## 빌드 설정 변경

### 다른 파일 실행하기

새로운 Kotlin 파일을 실행하려면 **build.gradle.kts의 한 줄만 변경**하면 됩니다:

**변경 전**:
```gradle
val applicationMainClass = "Test1025Kt"
```

**변경 후**:
```gradle
val applicationMainClass = "MyNewProjectKt"
```

그 다음 `./gradlew run` 실행.

### 자주 사용하는 Gradle 명령어

```bash
# 프로젝트 빌드
./gradlew build

# 프로젝트 실행
./gradlew run

# 빌드 캐시 삭제 후 재빌드
./gradlew clean build

# 독립 실행 JAR 파일 생성
./gradlew shadowJar
```

---

## 유용한 팁

### 1. 리소스 파일 (폰트, 이미지) 사용

데이터 폴더에 리소스를 저장하고 상대 경로로 로드합니다:

```kotlin
val font = loadFont("data/fonts/MyFont.otf", 55.0)
val image = loadImage("data/images/photo.png")
```

### 2. 마우스 입력

```kotlin
extend {
    // 마우스 위치
    val pos = mouse.position

    // 마우스 버튼 확인
    if (mouse.buttonDown) {
        drawer.circle(pos, 20.0)
    }
}
```

### 3. 애니메이션

```kotlin
extend {
    // 경과 시간 (초)
    val t = seconds
    val x = Math.cos(t) * 100 + width / 2.0
    drawer.circle(x, height / 2.0, 20.0)
}
```

### 4. 드로어 설정

```kotlin
extend {
    // 배경색
    drawer.clear(ColorRGBa(0.2, 0.2, 0.2))

    // 채우기 색
    drawer.fill = ColorRGBa.WHITE

    // 테두리
    drawer.stroke = ColorRGBa.RED
    drawer.strokeWeight = 2.0

    // 그리기
    drawer.circle(x, y, radius)
}
```

---

## 문제 해결

### `ClassNotFoundException: XxxKt` 오류

**원인**: `build.gradle.kts`의 `applicationMainClass`가 잘못되었습니다.

**해결**:
1. 파일명 확인: `Test1025.kt` 존재하는지?
2. Main class명 확인: `"Test1025Kt"` 정확한지? (파일명 + "Kt")
3. 파일이 `src/main/kotlin/` 폴더에 있는지?

### 첫 빌드가 느림

의존성을 처음 다운로드하는 중입니다. 완료 후에는 캐시되어 빠릅니다.

### 폰트/이미지 파일을 찾을 수 없음

데이터 폴더 경로 확인:
- 파일이 `Practice/data/fonts/` 또는 `Practice/data/images/`에 있는지?
- 코드의 경로가 정확한지? (상대 경로: `"data/fonts/..."`

---

## 다음 단계

1. [OPENRNDR 공식 문서](https://openrndr.org/) 확인
2. `data/` 폴더에 나만의 리소스 추가
3. 새로운 `.kt` 파일 만들어서 실험하기

Happy coding! 🎨
