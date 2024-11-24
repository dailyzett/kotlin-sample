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

    const handleKeypadRequest = async () => {
        try {
            const reponse = await fetch('/keypad', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
            });

            if (!reponse.ok) {
                throw new Error('키패드 요청에 실패했습니다.');
            }

            const data = await reponse.json();
            console.log('키패드 요청 성공: ', data);
        } catch (e) {
            console.error(e)
        }
    }

    return (
        <>
            <div>
                <h1>스프링 부트에서 받은 메시지:</h1>
                <p>{message}</p>
            </div>
            <button
                onClick={handleKeypadRequest}
                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded m-2.5">
                가상 키패드 요청
            </button>
        </>
    );

}

export default App
