//
//  CodeEditText.swift
//  iosExample
//
//  Created by Tomasz Kądziołka on 03/01/2024.
//

import SwiftUI
import kodeview

struct CodeEditText: UIViewControllerRepresentable {
    @Binding private var highlights: Highlights
    private var onValueChange: (String) -> Void

    init(
        newHighlights: Binding<Highlights>,
        onValueChange: @escaping (String) -> Void
    ) {
        self._highlights = newHighlights
        self.onValueChange = onValueChange
    }

    func makeUIViewController(context: Context) -> UIViewController {
        return UIViewController()
    }

    func updateUIViewController(_ wrapper: UIViewController, context: Context) {
         let kotlinController = CodeEditTextIos.shared.uiViewController(
             highlights: highlights,
             onValueChange: { value in
                 onValueChange(value.text)
             }
         )
         // Cleanup
         kotlinController.removeFromParent()
         kotlinController.view.removeFromSuperview()
         // Update view
         wrapper.addChild(kotlinController)
         wrapper.view.addSubview(kotlinController.view)
         // Match with frame
         kotlinController.view.frame = wrapper.view.frame
         kotlinController.didMove(toParent: wrapper)
    }
}
