import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import LogoutComponent from './LogoutComponent'
import HeaderComponent from './HeaderComponent'
import ListTodosComponent from './ListTodosComponent'
import ErrorComponent from './ErrorComponent'
import WelcomeComponent from './WelcomeComponent'
import LoginComponent from './LoginComponent'
import TodoComponent from './TodoComponent'
import AuthProvider, { useAuth } from './security/AuthContext'
import './TodoApp.css'
import RegisterComponent from './RegisterComponent'
import ForgotPasswordComponent from './ForgotPasswordComponent'
import ListCategoryComponent from './ListCategoryComponent'
import CategoryComponent from './CategoryComponent'
import ListTodosOfCategoryComponent from './ListTodosOfCategoryComponent'
import ListNoteComponent from './ListNoteComponent'
import NoteComponent from './NoteComponent'
import UserProfileComponent from './UserProfileComponent'
import ChangePasswordComponent from './ChangePasswordComponent'
import ResetPasswordComponent from './ResetPasswordComponent'


function AuthenticatedRoute({ children }) {
    const authContext = useAuth();
    console.log("--------", authContext)
    if (authContext.isAuthenticated)
        return children
    return <Navigate to='/' />
}

export default function TodoApp() {
    return (
        <div className="TodoApp">

            <AuthProvider>
                <BrowserRouter>
                    <HeaderComponent />
                    <Routes>
                        <Route path='/' element={<LoginComponent />}></Route>
                        <Route path='/login' element={<LoginComponent />}></Route>
                        <Route path='/register' element={<RegisterComponent />}></Route>
                        <Route path='/forgot-password' element={<ForgotPasswordComponent />}></Route>
                        <Route path='/reset-password' element={
                            <ResetPasswordComponent/>
                        }></Route>




                        <Route path='/welcome' element={
                            <AuthenticatedRoute>
                                <WelcomeComponent />
                            </AuthenticatedRoute>
                        }></Route>
                        <Route path='todos' element={
                            <AuthenticatedRoute>
                                <ListTodosComponent />
                            </AuthenticatedRoute>
                        }></Route>

                        <Route path='/profile' element={
                            <AuthenticatedRoute>
                                <UserProfileComponent />
                            </AuthenticatedRoute>
                        }></Route>

                        <Route path='/change-password' element={
                            <AuthenticatedRoute>
                                <ChangePasswordComponent />
                            </AuthenticatedRoute>
                        }></Route>

                        <Route path='category' element={
                            <AuthenticatedRoute>
                                <ListCategoryComponent />
                            </AuthenticatedRoute>
                        }></Route>

                        <Route path='/category/:id' element={
                            <AuthenticatedRoute>
                                <CategoryComponent />
                            </AuthenticatedRoute>
                        }></Route>

                        <Route path='/category/:id/todo' element={
                            <AuthenticatedRoute>
                                <ListTodosOfCategoryComponent />
                            </AuthenticatedRoute>
                        }></Route>

                        <Route path='/todo/:id/note' element={
                            <AuthenticatedRoute>
                                <ListNoteComponent />
                            </AuthenticatedRoute>
                        }></Route>

                        <Route path='/todo/:id/note/create' element={
                            <AuthenticatedRoute>
                                <NoteComponent />
                            </AuthenticatedRoute>
                        }
                        ></Route>

                        <Route path='/todo/:id' element={
                            <AuthenticatedRoute>
                                <TodoComponent />
                            </AuthenticatedRoute>
                        }></Route>


                        <Route path='logout' element={
                            <AuthenticatedRoute>
                                <LogoutComponent />
                            </AuthenticatedRoute>
                        }></Route>


                        <Route path='*' element={<ErrorComponent />}></Route>
                    </Routes>
                </BrowserRouter>
            </AuthProvider>

        </div>
    )
}

