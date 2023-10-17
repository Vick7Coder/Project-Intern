import { useEffect, useState } from "react";
import {retrieveAllNoteForTodo, deleteNoteApi} from "./api/NoteApiService";
import { useNavigate, useParams } from "react-router-dom";

function ListNoteComponent() {
    const { id } = useParams()

    const [notes, setNotes] = useState([])
    const [message, setMessage] = useState(null)
    const navigate = useNavigate();
    useEffect(() => refreshNotes(), [])

    function refreshNotes() {
        retrieveAllNoteForTodo(id)
            .then(response => {
                setNotes(response.data)
            })
            .catch(error => console.log(error))

    }
    function deleteNote(id) {
        deleteNoteApi(id)
            .then(
                () => {
                    setMessage(`Deletion of Note with ID = ${id} was successful!`)
                    refreshNotes()
                }

                //1: Display message
                //2: Update Todos list
            )
            .catch(error => console.log(error))

    }

    function addNewNote(id) {
        navigate(`/todo/${id}/note/create`)

    }
    


    return (
        <div className="container">
            <h1>Note of Todo </h1>
            {message && <div className="alert alert-warning">{message}</div>}
            <div>
                <table className='table'>
                    <thead>
                        <tr>
                            <th>Description</th>
                            <th>Create Date</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            notes.map(
                                note => {
                                    const createDate = new Date(note.dateTime);
                                    return (
                                        <tr key={note.id}>
                                            <td>{note.description}</td>
                                            <td>{new Date(note.dateTime).toLocaleString()}</td>
                                            <td><button className="btn btn-dark custom-button" onClick={() => deleteNote(note.id)} >Delete</button></td>
                
                                        </tr>
                                    )
                                })
                        }
                    </tbody>
                </table>
            </div>
            <button type="button" className="btn btn-secondary btn-lg custom-button m-5" onClick={() => addNewNote(id)} >Add New Note</button>
        </div>
    )
}

export default ListNoteComponent