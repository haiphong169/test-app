package com.haiphong.mentalhealthapp

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.haiphong.mentalhealthapp.view.screens.IntroScreen
import com.haiphong.mentalhealthapp.view.screens.admin.AdminHomeScreen
import com.haiphong.mentalhealthapp.view.screens.admin.RequestScreen
import com.haiphong.mentalhealthapp.view.screens.customer.bookappointments.AppointmentDetailsScreen
import com.haiphong.mentalhealthapp.view.screens.customer.bookappointments.AppointmentsListScreen
import com.haiphong.mentalhealthapp.view.screens.customer.bookappointments.BookAppointmentsScreen
import com.haiphong.mentalhealthapp.view.screens.customer.bookappointments.SpecialistInfoScreen
import com.haiphong.mentalhealthapp.view.screens.customer.content.ArticleScreen
import com.haiphong.mentalhealthapp.view.screens.customer.profile.CustomerEditProfileScreen
import com.haiphong.mentalhealthapp.view.screens.customer.intro.CustomerFillInfoScreen
import com.haiphong.mentalhealthapp.view.screens.customer.content.CustomerHomeScreen
import com.haiphong.mentalhealthapp.view.screens.customer.content.PostScreen
import com.haiphong.mentalhealthapp.view.screens.customer.content.TopicScreen
import com.haiphong.mentalhealthapp.view.screens.customer.profile.CustomerProfileScreen
import com.haiphong.mentalhealthapp.view.screens.customer.intro.CustomerSignInScreen
import com.haiphong.mentalhealthapp.view.screens.customer.intro.CustomerSignUpScreen
import com.haiphong.mentalhealthapp.view.screens.customer.journal.CustomerAddJournalScreen
import com.haiphong.mentalhealthapp.view.screens.customer.journal.CustomerEditJournalScreen
import com.haiphong.mentalhealthapp.view.screens.customer.journal.CustomerJournalDetailScreen
import com.haiphong.mentalhealthapp.view.screens.customer.journal.CustomerJournalListScreen
import com.haiphong.mentalhealthapp.view.screens.customer.profile.MoodsScreen
import com.haiphong.mentalhealthapp.view.screens.specialist.SpecialistHomeScreen
import com.haiphong.mentalhealthapp.view.screens.specialist.intro.SpecialistFillInfoScreen
import com.haiphong.mentalhealthapp.view.screens.specialist.intro.SpecialistSignUpScreen
import com.haiphong.mentalhealthapp.view.screens.specialist.profile.SpecialistEditProfileScreen
import com.haiphong.mentalhealthapp.view.screens.specialist.profile.SpecialistProfileScreen
import com.haiphong.mentalhealthapp.view.util.viewmodel.CustomerFillInfoViewModel
import com.haiphong.mentalhealthapp.viewmodel.intro.CustomerSignInViewModel
import com.haiphong.mentalhealthapp.viewmodel.intro.CustomerSignUpViewModel
import com.haiphong.mentalhealthapp.viewmodel.journal.CustomerJournalListViewModel

enum class Route {
    Intro,
    SignIn,
    SignUp,
    FillInfo,
    Home,
    JournalList,
    Profile,
    MoodList,
    EditProfile,
    AddJournal,
    Journal,
    EditJournal,
    Topic,
    Unit,
    BookAppointment,
    Post,
    Article,
    SpecialistSignUp,
    SpecialistFillInfo,
    SpecialistHome,
    AdminHome,
    Request,
    SpecialistProfile,
    SpecialistEditProfile,
    SpecialistInfo,
    AppointmentsList,
    AppointmentDetails
}


@Composable
fun MentalHealthApp(
    navController: NavHostController = rememberNavController(),
    introViewModel: IntroViewModel = viewModel()
) {
    val introState by introViewModel.introState.collectAsState()

    if (introState.status == "loading") {
        CircularProgressIndicator()
    } else if (introState.status == "done") {
        NavHost(
            navController = navController,
            startDestination = when (UserType.Customer) {
                UserType.None -> Route.Intro.name
                UserType.Customer -> "content"
                UserType.Specialist -> "specialistHome"
                UserType.Admin -> "admin"
            },
        ) {
            composable(route = Route.Intro.name) {
                IntroScreen(onNextButtonClicked = {
                    navController.navigate("customerIntro")
                })
            }
            customerIntro(navController)
            content(navController)
            bookAppointments(navController)
            journal(navController)
            profile(navController)
            specialistIntro(navController)
            specialistHome(navController)
            admin(navController)
            specialistProfile(navController)
        }
    }
}

fun NavGraphBuilder.customerIntro(navController: NavController) {
    navigation(startDestination = Route.SignIn.name, route = "customerIntro") {
        composable(route = Route.SignIn.name) {
            val customerSignInViewModel: CustomerSignInViewModel = viewModel()
            val signInState by customerSignInViewModel.signInState.collectAsState()

            CustomerSignInScreen(
                toSignUp = {
                    navController.navigate(Route.SignUp.name)
                },
                onSignIn = {
                    val destination = if (it == "customer") "content" else "specialistHome"

                    navController.navigate(destination) {
                        popUpTo(Route.Intro.name) {
                            inclusive = true
                        }
                    }
                },
                signInState = signInState,
                onEmailChange = customerSignInViewModel::onEmailChange,
                onPasswordChange = customerSignInViewModel::onPasswordChange,
                clearForm = customerSignInViewModel::clearForm,
                setErrorMessage = customerSignInViewModel::setErrorMessage,
                adminSignIn = {
                    navController.navigate("admin") {
                        popUpTo(Route.Intro.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Route.SignUp.name) {
            val customerSignUpViewModel: CustomerSignUpViewModel = viewModel()
            val signUpState by customerSignUpViewModel.signUpState.collectAsState()

            CustomerSignUpScreen(
                signUpState = signUpState,
                onSignUp = {
                    navController.navigate(Route.FillInfo.name) {
                        popUpTo(Route.Intro.name) {
                            inclusive = false
                        }
                    }
                },
                createNewUser = customerSignUpViewModel::createNewUser,
                onEmailChange = customerSignUpViewModel::onEmailChange,
                onPasswordChange = customerSignUpViewModel::onPasswordChange,
                onConfirmPasswordChange = customerSignUpViewModel::onConfirmPasswordChange,
                clearForm = customerSignUpViewModel::clearForm,
                setErrorMessage = customerSignUpViewModel::setErrorMessage,
                toSpecialistForm = {
                    navController.navigate("specialistIntro")
                }
            )
        }
        composable(route = Route.FillInfo.name) {
            val customerFillInfoViewModel: CustomerFillInfoViewModel = viewModel()
            val fillInfoState by customerFillInfoViewModel.fillInfoState.collectAsState()

            CustomerFillInfoScreen(
                onNextButtonClicked = {
                    navController.navigate("content") {
                        popUpTo(Route.Intro.name) {
                            inclusive = true
                        }
                    }
                },
                fillInfoState = fillInfoState,
                clearForm = customerFillInfoViewModel::clearForm,
                setErrorMessage = customerFillInfoViewModel::setErrorMessage,
                onFillUserInfo = customerFillInfoViewModel::updateUser,
                onNameChange = customerFillInfoViewModel::onNameChange,
                onGenderChange = customerFillInfoViewModel::onGenderChange,
                onDateChange = customerFillInfoViewModel::onDateChange,
                onMonthChange = customerFillInfoViewModel::onMonthChange,
                onYearChange = customerFillInfoViewModel::onYearChange,
                onBioChange = customerFillInfoViewModel::onBioChange,
                onAvatarPathChange = customerFillInfoViewModel::onAvatarChange
            )
        }

    }
}

fun NavGraphBuilder.content(navController: NavController) {
    navigation(startDestination = Route.Home.name, route = "content") {
        composable(route = Route.Home.name) {
            CustomerHomeScreen(navController = navController, toTopic = { topicId ->
                navController.navigate("${Route.Topic.name}/$topicId")
            })
        }
        composable(route = "${Route.Topic.name}/{topicName}") {
            TopicScreen(toPost = { postTitle, topicName ->
                navController.navigate("${Route.Post.name}/$topicName/$postTitle")
            }, toArticle = { topicName, articleTitle ->
                navController.navigate("${Route.Article.name}/$topicName/$articleTitle")
            })
        }
        composable(route = "${Route.Post.name}/{topicName}/{postTitle}") {
            PostScreen()
        }
        composable(route = "${Route.Article.name}/{topicName}/{articleTitle}") {
            ArticleScreen()
        }
    }
}

fun NavGraphBuilder.journal(navController: NavController) {
    navigation(startDestination = Route.JournalList.name, route = "journal") {
        composable(route = Route.JournalList.name) {
            val viewModel: CustomerJournalListViewModel = viewModel()
            val journalListState by viewModel.journalState.collectAsState()

            CustomerJournalListScreen(journalListState = journalListState, onAddJournal = {
                navController.navigate(Route.AddJournal.name)
            }, toJournal = { journalId ->
                navController.navigate("${Route.Journal.name}/$journalId")
            }, navController = navController)
        }
        composable(route = Route.AddJournal.name) {
            CustomerAddJournalScreen(onSave = {
                navController.navigate(Route.JournalList.name) {
                    popUpTo(Route.JournalList.name) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = "${Route.Journal.name}/{journalId}") {
            CustomerJournalDetailScreen(toEdit = { journalId ->
                navController.navigate("${Route.EditJournal.name}/$journalId")
            }, onDelete = {
                navController.navigate(Route.JournalList.name) {
                    popUpTo(Route.JournalList.name) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = "${Route.EditJournal.name}/{journalId}") {
            CustomerEditJournalScreen(onSave = { journalId ->
                navController.navigate(Route.JournalList.name) {
                    popUpTo(Route.JournalList.name) {
                        inclusive = true
                    }
                    navController.navigate("${Route.Journal.name}/$journalId")
                }
            })
        }
    }
}

fun NavGraphBuilder.profile(navController: NavController) {
    navigation(startDestination = Route.Profile.name, route = "customerProfile") {
        composable(route = Route.Profile.name) {
            CustomerProfileScreen(onSignOut = {
                navController.navigate(Route.Intro.name) {
                    popUpTo(Route.Home.name) {
                        inclusive = true
                    }
                }
            }, toEditScreen = {
                navController.navigate(Route.EditProfile.name)
            }, toMoodsScreen = {
                navController.navigate(Route.MoodList.name)
            }, navController = navController)
        }

        composable(route = Route.EditProfile.name) {
            CustomerEditProfileScreen(onSave = {
                navController.navigate(Route.Profile.name) {
                    popUpTo(Route.Profile.name) {
                        inclusive = true
                    }
                }
            }, onCancel = { navController.navigateUp() })
        }
        composable(route = Route.MoodList.name) {
            MoodsScreen()
        }
    }
}

fun NavGraphBuilder.bookAppointments(navController: NavController) {
    navigation(startDestination = Route.BookAppointment.name, route = "bookAppointment") {
        composable(route = Route.BookAppointment.name) {
            BookAppointmentsScreen(navController = navController)
        }
        composable(route = "${Route.SpecialistInfo.name}/{specialistId}") {
            SpecialistInfoScreen(onComplete = {
                navController.navigateUp()
            })
        }
        composable(route = Route.AppointmentsList.name) {
            AppointmentsListScreen(toAppointment = { appointmentId ->
                navController.navigate("${Route.AppointmentDetails.name}/$appointmentId")
            })
        }
        composable(route = "${Route.AppointmentDetails.name}/{appointmentId}") {
            AppointmentDetailsScreen(onDelete = {
                navController.navigate(Route.AppointmentsList.name) {
                    popUpTo(Route.AppointmentsList.name) {
                        inclusive = true
                    }
                }
            }, isCustomerVisiting = true)
        }
    }
}

fun NavGraphBuilder.specialistIntro(navController: NavController) {
    navigation(startDestination = Route.SpecialistSignUp.name, route = "specialistIntro") {
        composable(route = Route.SpecialistSignUp.name) {
            SpecialistSignUpScreen(toFillInfo = {
                navController.navigate(Route.SpecialistFillInfo.name) {
                    popUpTo(Route.Intro.name) {
                        inclusive = false
                    }
                }
            })
        }
        composable(route = Route.SpecialistFillInfo.name) {
            SpecialistFillInfoScreen(onCompleteForm = {
                navController.navigate(route = Route.Intro.name) {
                    popUpTo(Route.Intro.name) {
                        inclusive = true
                    }
                }
            })
        }

    }
}

fun NavGraphBuilder.specialistHome(navController: NavController) {
    navigation(startDestination = Route.SpecialistHome.name, route = "specialistHome") {
        composable(route = Route.SpecialistHome.name) {
            SpecialistHomeScreen(onSignOut = {
                navController.navigate(Route.Intro.name) {
                    popUpTo(Route.SpecialistHome.name) {
                        inclusive = true
                    }
                }
            }, toProfile = {
                navController.navigate("specialistProfile")
            }, toAppointment = { appointmentId ->
                navController.navigate("${Route.AppointmentDetails.name}/specialistVisiting/$appointmentId")
            })
        }

        composable(route = "${Route.AppointmentDetails.name}/specialistVisiting/{appointmentId}") {
            AppointmentDetailsScreen(onDelete = { /*TODO*/ }, isCustomerVisiting = false)
        }
    }
}

fun NavGraphBuilder.specialistProfile(navController: NavController) {
    navigation(startDestination = Route.SpecialistProfile.name, route = "specialistProfile") {
        composable(route = Route.SpecialistProfile.name) {
            SpecialistProfileScreen(toEdit = {
                navController.navigate(Route.SpecialistEditProfile.name)
            })
        }
        composable(route = Route.SpecialistEditProfile.name) {
            SpecialistEditProfileScreen(onComplete = {
                navController.navigate(Route.SpecialistProfile.name) {
                    popUpTo(Route.SpecialistProfile.name) {
                        inclusive = true
                    }
                }
            })
        }
    }
}

fun NavGraphBuilder.admin(navController: NavController) {
    navigation(startDestination = Route.AdminHome.name, route = "admin") {
        composable(route = Route.AdminHome.name) {
            AdminHomeScreen(toRequest = { requestId ->
                navController.navigate("${Route.Request.name}/$requestId")
            }, signOut = {
                navController.navigate(Route.Intro.name) {
                    popUpTo(Route.AdminHome.name) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = "${Route.Request.name}/{requestId}") {
            RequestScreen(onComplete = {
                navController.navigate(Route.AdminHome.name) {
                    popUpTo(Route.AdminHome.name) {
                        inclusive = true
                    }
                }
            })
        }
    }
}
