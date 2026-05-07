group = "app.morphe"

patches {
    about {
        name = "Remove Permanent Ban Banner Patch"
        description = "A small third-party Morphe patch source for the official Reddit Android app."
        source = "https://github.com/<owner>/remove-permaban-banner-patch"
        author = "Bernardo Santos"
        contact = "na"
        website = "https://github.com/<owner>/remove-permaban-banner-patch"
        license = "GNU General Public License v3.0"
    }
}

dependencies {
    implementation(libs.gson)
    implementation(libs.guava)
    implementation(libs.morphe.patches.library)
    compileOnly(project(":patches:stub"))
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("app.morphe.util.PatchListGeneratorKt")

        doLast {
            rootProject.file("patches-list.json").writeText(
                """
                {
                  "version": "${project.version}",
                  "patches": [
                    {
                      "name": "Hide permanent ban banner",
                      "description": "Hides Reddit's persistent permanent-ban account banner without changing account state or API behavior.",
                      "use": true,
                      "dependencies": [
                        "BytecodePatch"
                      ],
                      "compatiblePackages": {
                        "com.reddit.frontpage": [
                          "2026.10.0"
                        ]
                      },
                      "options": []
                    }
                  ]
                }
                """.trimIndent() + "\n"
            )
        }
    }

    publish {
        dependsOn("generatePatchesList")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xcontext-parameters")
    }
}
