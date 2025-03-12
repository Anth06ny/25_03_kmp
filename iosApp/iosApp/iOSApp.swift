import SwiftUI
//Import du nom du package contenant src/commonMain
import ComposeApp

@main
struct iOSApp: App {

    init(){
        //initKoin() dans le fichier koin.kt donne
        KoinKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}