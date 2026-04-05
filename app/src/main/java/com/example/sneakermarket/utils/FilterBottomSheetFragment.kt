package com.example.sneakermarket.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sneakermarket.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText

class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    // 1. Define an interface to send data back
    interface OnFilterApplyListener {
        fun onApply(
            minPrice: Double?,
            maxPrice: Double?,
            size: Double?,
            color: String?
        )
    }

    private var listener: OnFilterApplyListener? = null

    // Using lateinit here. If findViewById fails, app will crash,
    // which is good for debugging (it means the ID is wrong in the XML)
    private lateinit var minPriceInput: TextInputEditText
    private lateinit var maxPriceInput: TextInputEditText
    private lateinit var sizeChipGroup: ChipGroup
    private lateinit var colorChipGroup: ChipGroup
    private lateinit var applyFiltersButton: Button
    private lateinit var clearFiltersButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 2. Attach the listener
        listener = context as? OnFilterApplyListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find all views - this is where a crash would happen
        // if an ID in fragment_filter.xml is wrong.
        minPriceInput = view.findViewById(R.id.minPriceInput)
        maxPriceInput = view.findViewById(R.id.maxPriceInput)
        sizeChipGroup = view.findViewById(R.id.sizeChipGroup)
        colorChipGroup = view.findViewById(R.id.colorChipGroup)
        applyFiltersButton = view.findViewById(R.id.applyFiltersButton)
        clearFiltersButton = view.findViewById(R.id.clearFiltersButton)

        // 3. Set Apply button click listener
        applyFiltersButton.setOnClickListener {
            val minPrice = minPriceInput.text.toString().toDoubleOrNull()
            val maxPrice = maxPriceInput.text.toString().toDoubleOrNull()

            // Get selected size
            val selectedSizeId = sizeChipGroup.checkedChipId
            val size = if (selectedSizeId != View.NO_ID) {
                view.findViewById<Chip>(selectedSizeId).text.toString().toDoubleOrNull()
            } else {
                null
            }

            // Get selected color
            val selectedColorId = colorChipGroup.checkedChipId
            val color = if (selectedColorId != View.NO_ID) {
                view.findViewById<Chip>(selectedColorId).text.toString()
            } else {
                null
            }

            // Send data back to the activity
            listener?.onApply(minPrice, maxPrice, size, color)
            dismiss() // Close the bottom sheet
        }

        // 4. Set Clear button click listener
        clearFiltersButton.setOnClickListener {
            minPriceInput.text?.clear()
            maxPriceInput.text?.clear()
            sizeChipGroup.clearCheck()
            colorChipGroup.clearCheck()

            // Send null values back to clear filters
            listener?.onApply(null, null, null, null)
            dismiss()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        const val TAG = "FilterBottomSheet"
    }
}