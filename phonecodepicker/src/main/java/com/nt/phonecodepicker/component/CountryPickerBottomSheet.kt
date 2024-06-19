package com.nt.phonecodepicker.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nt.phonecodepicker.utils.Country
import com.nt.phonecodepicker.utils.countryList
import com.nt.phonecodepicker.utils.getCountryNameString
import com.nt.phonecodepicker.utils.getFlagIcon
import com.nt.phonecodepicker.utils.searchCountry

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryPickerBottomSheet(
    title: @Composable () -> Unit,
    show: Boolean,
    hint: String = "",
    onItemSelected: (country: Country) -> Unit,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val countries = remember { countryList(context) }
    val countriesList = countries.toList()
    var selectedCountry by remember { mutableStateOf(countries[0]) }
    var searchValue by remember { mutableStateOf("") }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    LaunchedEffect(key1 = show) {
        if (show) modalBottomSheetState.show()
        else modalBottomSheetState.hide()
    }

    LaunchedEffect(key1 = modalBottomSheetState.currentValue) {
        if (modalBottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            onDismissRequest()
        }
    }
    ModalBottomSheetLayout(
        modifier = Modifier.imePadding(),
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            title()
            Column {
                searchValue = countrySearchView(modalBottomSheetState, hint)
                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        if (searchValue.isEmpty()) {
                            countriesList
                        } else {
                            countriesList.searchCountry(searchValue)
                        }
                    ) { country ->
                        Row(modifier = Modifier
                            .clickable {
                                selectedCountry = country
                                onItemSelected(selectedCountry)
                            }
                            .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painterResource(country.getFlagIcon()),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(25.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = country.getCountryNameString(),
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .weight(2f)
                            )
                        }
                        Divider(
                            color = Color.LightGray, thickness = 0.5.dp
                        )
                    }
                }
            }

        }
    ) {
        content()
    }
}
