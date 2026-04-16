import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all) // Compose has own keyboard handling
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        // Set up notification observer for Google Sign-In requests from Kotlin
        NotificationCenter.default.addObserver(
            forName: NSNotification.Name("GoogleSignInRequest"),
            object: nil,
            queue: .main
        ) { notification in
            GoogleSignInBridge.shared.performSignIn { idToken, accessToken, error in
                // Post result back to Kotlin
                NotificationCenter.default.post(
                    name: NSNotification.Name("GoogleSignInResult"),
                    object: nil,
                    userInfo: ["idToken": idToken as Any,"accessToken": accessToken as Any, "error": error as Any]
                )
            }
        }

        // Create the Compose view controller
        let viewController = MainViewControllerKt.MainViewController()

        // Store the view controller in the bridge for Google Sign-In
        GoogleSignInBridge.shared.presentingViewController = viewController

        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
