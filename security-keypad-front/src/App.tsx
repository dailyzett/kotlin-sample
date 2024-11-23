import {useEffect, useState} from 'react'
import './App.css'

function App() {
    const [message, setMessage] = useState<string>('');

    useEffect(() => {
        fetch("/test/hello")
            .then((res) => res.text())
            .then(data => setMessage(data))
            .catch((error) => console.log('Error:', error));
    }, []);

    return (
        <div>
            <h1>스프링 부트에서 받은 메시지:</h1>
            <p>{message}</p>
        </div>
    );

}

export default App
