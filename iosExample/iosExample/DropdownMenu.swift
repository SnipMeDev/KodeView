//
//  DropdownMenu.swift
//  iosApp
//
//  Created by Tomasz Kądziołka on 07/05/2023.
//

import SwiftUI

struct DropdownMenu : View {
    var values: Array<String>
    var onSelect: (String) -> Void
    @State var selected: Int

    public init(
        values: Array<String>,
        defaultSelection: Int,
        onSelect: @escaping (String) -> Void
    ) {
        self.values = values
        self.selected = defaultSelection
        self.onSelect = onSelect
    }
    
    var body: some View {
        Picker(selection: $selected, label: Text("")) {
            ForEach(values.indices) { index in
                Text("\(values[index])").tag(index)
            }
        }.onChange(of: selected) { newValue in
            onSelect(values[newValue])
        }
    }
}
