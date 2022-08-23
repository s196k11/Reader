package com.example.reader.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.reader.components.InputField
import com.example.reader.components.ReaderAppBar
import com.example.reader.model.Item
import com.example.reader.navigation.ReaderScreens


@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: BooksSearchViewModel = hiltViewModel(),
){

    Scaffold(
        topBar = {
                 ReaderAppBar(title = "Search Books",
                     icon = Icons.Default.ArrowBack,
                     navController = navController,
                     showProfile = false){
//                     navController.popBackStack()
                     navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                 }
        },
    ) {

        Surface() {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){searchQuery ->
                    viewModel.searchBooks(searchQuery)
                }

                Spacer(modifier = Modifier.height(10.dp))
                BookList(navController,viewModel)

            }

        }
    }

}

@Composable
fun BookList(navController: NavController,viewModel: BooksSearchViewModel = hiltViewModel()) {

    val listOfBooks = viewModel.list

    if (viewModel.isLoading){
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }else{
        LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ){
            items(items = listOfBooks){book ->
                BookRow(book,navController)
            }
        }
    }




}

@Composable
fun BookRow(book: Item, navController: NavController) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { }
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp
    ) {
        Row(modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
        ){
            val imageurl: String = if(book.volumeInfo.imageLinks.smallThumbnail.isEmpty() == true) "http://books.google.com/books/content?id=oMYQz4_BW48C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            else{
                book.volumeInfo.imageLinks.smallThumbnail.toString()
            }

            Image(painter = rememberAsyncImagePainter(model = imageurl), contentDescription = "Book Image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 8.dp)
            )

            Column() {
                Text(text = book.volumeInfo.title.toString(), overflow = TextOverflow.Ellipsis)
                Text(text = "Author: " + book.volumeInfo.authors.toString(),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                Text(text = "Date: : " + book.volumeInfo.publishedDate.toString(),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                Text(text = book.volumeInfo.categories.toString(),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier:Modifier = Modifier,
    loading :Boolean =  false,
    hint:String = "Search",
    onSearch:(String) -> Unit = {}
){
    val searchQueryState = rememberSaveable {mutableStateOf("")}
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value){
        searchQueryState.value.trim().isNotEmpty()
    }

    Column() {

        InputField(valueState = searchQueryState, labelId = "Search", enabled = true,
            onAction = KeyboardActions{
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}














