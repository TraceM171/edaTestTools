import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.31"
    id("org.openjfx.javafxplugin") version "0.0.9"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit"))
    implementation ("org.benf:cfr:0.151")
    implementation ("org.openjfx:javafx-plugin:0.0.9")
    implementation ("org.apache.bcel:bcel:6.5.0")
    implementation ("commons-lang:commons-lang:2.6")
}

javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}