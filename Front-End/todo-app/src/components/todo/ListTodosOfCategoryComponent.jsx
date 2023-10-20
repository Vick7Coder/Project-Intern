import { useEffect, useState } from "react";
import { deleteTodoApi } from "./api/TodoApiService";
import { retrieveAllTodoForCategory } from './api/CategoryApiService';
import { useNavigate, useParams } from "react-router-dom";

function ListTodosOfCategoryComponent() {
    const { id } = useParams()

    const [todos, setTodos] = useState([])
    const [message, setMessage] = useState(null)
    const navigate = useNavigate();
    useEffect(() => refreshTodos(), [])

    function refreshTodos() {
        retrieveAllTodoForCategory(id)
            .then(response => {
                setTodos(response.data)
            })
            .catch(error => console.log(error))

    }
    function deleteTodo(id) {
        deleteTodoApi(id)
            .then(
                () => {
                    setMessage(`Deletion of todo with ID = ${id} was successful!`)
                    refreshTodos()
                }

                //1: Display message
                //2: Update Todos list
            )
            .catch(error => console.log(error))

    }

    function updateTodo(id) {
        navigate(`/todo/${id}`)

    }
    function addNewTodo() {
        navigate('/todo/-1')

    }
    function noteTodo(id){
        navigate(`/todo/${id}/note`)
    }


    return (
        <div className="container">
            <h1>Todo of Category </h1>
            {message && <div className="alert alert-warning">{message}</div>}
            <div>
                <table className='table'>
                    <thead>
                        <tr>
                            <th>Description</th>
                            <th>Create Date</th>
                            <th>Target Date</th>
                            <th>Category</th>
                            <th>Note</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            Array.isArray(todos) && todos.map(
                                todo => {
                                    const targetDate = new Date(todo.targetDate);
                                    return (
                                        <tr key={todo.id}>
                                            <td>{todo.description}</td>
                                            <td>{new Date(todo.createDate).toLocaleString()}</td>
                                            <td>{targetDate.toLocaleString()}</td>
                                            <td>{todo.category.name}</td>
                                            <td><button className="btn btn-dark custom-button" onClick={() => noteTodo(todo.id)} >Note</button></td>
                                            <td><button className="btn btn-dark custom-button" onClick={() => deleteTodo(todo.id)} >Delete</button></td>
                                            <td><button className="btn btn-light custom-button" onClick={() => updateTodo(todo.id)} >Update</button></td>
                                        </tr>
                                    )
                                })
                        }
                    </tbody>
                </table>
            </div>
            <button type="button" className="btn btn-secondary btn-lg custom-button m-5" onClick={addNewTodo} >Add New Todo</button>
        </div>
    )
}

export default ListTodosOfCategoryComponent