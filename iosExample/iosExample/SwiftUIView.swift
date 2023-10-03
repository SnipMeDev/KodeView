//
//  SwiftUIView.swift
//  iosExample
//
//  Created by Tomasz Kądziołka on 01/10/2023.
//

import SwiftUI
import kodeview

struct UiTextView: UIViewControllerRepresentable {
    
    
         func makeUIViewController(context: Context) -> UIViewController {
             return ExampleKt.someText()
         }
    
         func updateUIViewController(_ wrapper: UIViewController, context: Context) {
    
    
}
}
