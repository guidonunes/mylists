package br.com.fiap.mylists.screens



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.mylists.ui.theme.MylistsTheme
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.mylists.model.Activity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }
    var activities = remember { mutableStateListOf<Activity>() }

    val database = Firebase
        .database("https://mylists-fiap-default-rtdb.firebaseio.com/")

    database.getReference("activities")
        .get().addOnSuccessListener { dataSnapshot ->
            activities.clear()
            for (child in dataSnapshot.children) {
                val activity = child.getValue(Activity::class.java)
                activities.add(activity!!)
            }
        }
        .addOnFailureListener {
            println("Failed to read value: ${it.message}")
        }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "My Lists",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        IconButton(
                            onClick = {
                                val authenticate = FirebaseAuth.getInstance()
                                authenticate.signOut()
                                navController.navigate("login")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Logout",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                shape = RoundedCornerShape(50.dp),
                modifier = modifier
                    .width(100.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                text = "Activities",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 48.dp)
            ) {
                items(activities) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = it.subject,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = it.deadline,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Apagar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }

    if(showDialog) {
        ActivityDialog(
            onDismiss = {
                showDialog = false
            },
            onConfirm = {
                showDialog = false
            },
            action = "New Activity"
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MylistsTheme () {
        HomeScreen(navController = rememberNavController())
    }
}