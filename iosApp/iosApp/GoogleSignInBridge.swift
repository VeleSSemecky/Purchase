import Foundation
import GoogleSignIn
import FirebaseAuth
import UIKit

/// Type alias for the sign-in completion handler
public typealias SignInCompletion = (String?, String?, String?) -> Void

/// Type alias for the sign-in closure that can be called from Kotlin
public typealias SignInClosure = (UIViewController, @escaping SignInCompletion) -> Void

/// Google Sign-In bridge for Kotlin Multiplatform
/// This class is accessible from Kotlin/Native via Objective-C interop
@objc(GoogleSignInBridge)
public class GoogleSignInBridge: NSObject {

    /// Singleton instance accessible from Kotlin
    @objc public static let shared = GoogleSignInBridge()

    /// The presenting view controller for Google Sign-In
    @objc public weak var presentingViewController: UIViewController?

    /// Sign-in closure that can be called from anywhere
    /// Set this during app initialization
    public var signInClosure: SignInClosure?

    /// Result holder for synchronous-style access from Kotlin
    @objc public private(set) var lastIdToken: String?
    @objc public private(set) var lastAccessToken: String?
    @objc public private(set) var lastError: String?
    @objc public private(set) var isSignInComplete: Bool = false

    private override init() {
        super.init()

        // Set up default sign-in closure
        signInClosure = { [weak self] viewController, completion in
            self?.performSignIn(presentingViewController: viewController, completion: completion)
        }
    }

    /// Performs Google Sign-In using stored presentingViewController
    /// @param completion Completion handler called when sign-in completes
    @objc public func performSignIn(completion: @escaping (String?, String?, String?) -> Void) {
        guard let viewController = presentingViewController else {
            completion(nil, nil, "Presenting view controller not set")
            return
        }
        performSignIn(presentingViewController: viewController, completion: completion)
    }

    /// Performs Google Sign-In
    /// @param viewController The presenting view controller
    /// @param completion Completion handler called when sign-in completes
    @objc public func performSignIn(
        presentingViewController: UIViewController,
        completion: @escaping (String?, String?, String?) -> Void
    ) {
        // Reset state
        lastIdToken = nil
        lastError = nil
        isSignInComplete = false

        // Get client ID from Info.plist
        guard let clientID = Bundle.main.object(forInfoDictionaryKey: "GIDClientID") as? String else {
            lastError = "GIDClientID not found in Info.plist"
            isSignInComplete = true
            completion(nil, nil, lastError)
            return
        }

        // Configure Google Sign-In
        let config = GIDConfiguration(clientID: clientID)
        GIDSignIn.sharedInstance.configuration = config

        // Perform sign-in on main thread
        DispatchQueue.main.async {
            GIDSignIn.sharedInstance.signIn(withPresenting: presentingViewController) { [weak self] result, error in
                guard let self = self else { return }

                if let error = error {
                    self.lastError = error.localizedDescription
                    self.isSignInComplete = true
                    completion(nil, nil, self.lastError)
                    return
                }

                guard let user = result?.user,
                    let idToken = user.idToken?.tokenString else {
                    self.lastError = "Failed to get ID token or access token from Google Sign-In"
                    self.isSignInComplete = true
                    completion(nil, nil, self.lastError)
                    return
                }

                print("User ID: \(user.userID ?? "")")
                print("ID Token: \(idToken)")
                print("Access Token: \(user.accessToken.tokenString)")

                self.lastAccessToken = user.accessToken.tokenString
                self.lastIdToken = idToken
                self.isSignInComplete = true
                completion(idToken, lastAccessToken, nil)
            }
        }
    }

    /// Signs out from Google
    @objc public func signOut() {
        GIDSignIn.sharedInstance.signOut()
        lastIdToken = nil
        lastAccessToken = nil
        lastError = nil
        isSignInComplete = false
    }

    /// Handle URL for Google Sign-In callback (call from SceneDelegate/AppDelegate)
    @objc public static func handle(_ url: URL) -> Bool {
        return GIDSignIn.sharedInstance.handle(url)
    }

    /// Check if there's a previous sign-in that can be restored
    @objc public func restorePreviousSignIn(completion: @escaping (String?, String?, String?) -> Void) {
        GIDSignIn.sharedInstance.restorePreviousSignIn { user, error in
            if let error = error {
                completion(nil, nil, error.localizedDescription)
                return
            }

            guard let user = user,
                let idToken = user.idToken?.tokenString else {
                completion(nil, nil, "No previous sign-in to restore")
                return
            }

            completion(idToken, user.accessToken.tokenString, nil)
        }
    }
}
