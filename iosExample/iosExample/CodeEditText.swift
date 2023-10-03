//
//  CodeEditText.swift
//  iosExample
//
//  Created by Tomasz Kądziołka on 25/09/2023.
//

// import SwiftUI
// import kodeview
//
// struct CodeEditText: UIViewControllerRepresentable {
//     @Binding private var highlights: Highlights
//     private var onValueChange: (String) -> () = { val in }
//
//     init(
//         newHighlights: Binding<Highlights>,
//         onValueChange: @escaping (String) -> ()
//     ) {
//         self.onValueChange = onValueChange
//         self._highlights = newHighlights
//     }
//
//     func makeUIViewController(context: Context) -> UIViewController {
//         return UIViewController()
//     }
//
//     func updateUIViewController(_ wrapper: UIViewController, context: Context) {
// //         let kotlinController = KodeView2Kt.codeEditText(
// //             highlights: highlights,
// //             onValueChange: onValueChange
// //         );
// //
// //         // Cleanup
// //         kotlinController.removeFromParent()
// //         kotlinController.view.removeFromSuperview()
// //         // Update view
// //         wrapper.addChild(kotlinController)
// //         wrapper.view.addSubview(kotlinController.view)
// //         // Match with frame
// //         kotlinController.view.frame = wrapper.view.frame
// //         kotlinController.didMove(toParent: wrapper)
//     }
// }
