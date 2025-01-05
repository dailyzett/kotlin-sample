import {useEffect, useRef, useState} from "react";
import {encryptWithPublicKey} from './utils/crypto.ts'

interface KeypadResponse {
    publicKey: string;
    keypadHtml: string;
    keypadId: string;  // 키패드 세션 식별을 위한 ID
}

export const Keypad = () => {
    const [keypadData, setKeypadData] = useState<KeypadResponse | null>(null);
    const keypadRef = useRef<HTMLDivElement>(null)

    const handleKeypadRequest = async () => {
        try {
            const response = await fetch('/api/v1/keypad', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
            });

            if (!response.ok) {
                throw new Error('키패드 요청에 실패했습니다.');
            }

            const data: KeypadResponse = await response.json();
            setKeypadData(data);

        } catch (e) {
            console.error(e)
        }
    }

    useEffect(() => {
        if (keypadData && keypadRef.current) {
            keypadRef.current.innerHTML = keypadData.keypadHtml;

            const keypadContainer = keypadRef.current.firstElementChild;
            if (keypadContainer) {
                keypadContainer.className = "grid gap-4 p-6 bg-white rounded-lg shadow";

                keypadContainer.querySelectorAll(":scope > div").forEach(row => {
                    row.className = "flex gap-4 justify-center";

                    row.querySelectorAll("button").forEach(button => {
                        button.className = [
                            // 크기 및 패딩
                            "w-16 h-16 p-4",
                            // 배경색 및 호버 효과
                            "bg-orange-500 hover:bg-orange-400 active:bg-orange-600",
                            // 텍스트 스타일
                            "text-white font-semibold text-xl",
                            // 테두리 및 그림자
                            "rounded-lg shadow-md",
                            // 트랜지션
                            "transition-all duration-200 ease-in-out",
                            // 호버시 살짝 위로 올라가는 효과
                            "hover:-translate-y-0.5"
                        ].join(" ");
                    });
                });
            }

            initializeKeypad(keypadData.publicKey, keypadData.keypadId)
        }
    }, [keypadData]);

    const initializeKeypad = (publicKey: string, keypadId: string) => {
        const buttons = keypadRef.current?.querySelectorAll('button')
        if (!buttons) return;

        buttons.forEach(button => {
            button.addEventListener('click', async (e) => {
                e.preventDefault();
                const value = button.getAttribute('data-value')
                if (value) {
                    const encryptedValue = await encryptWithPublicKey(value, publicKey);
                    handleKeyInput(encryptedValue, keypadId);
                }
            });
        });
    };

    const handleKeyInput = async (encryptedValue: string, keypadId: string) => {
        console.log('Encrypted input: ', encryptedValue)

        try {
            const response = await fetch('/api/v1/decrypt', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    keypadId,
                    encryptedValue,
                }),
            });

            if (!response.ok) {
                throw new Error('복호화 요청에 실패했습니다.');
            }

            const decryptedValue = await response.text();
            console.log('서버 응답:', decryptedValue);

        } catch (e) {
            console.error('복호화 중 오류 발생:', e);
        }
    }

    return (
        <>
            <div className="flex flex-col items-center gap-4">
                <button
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded m-2.5"
                    onClick={handleKeypadRequest}
                >
                    가상 키패드 요청
                </button>
                <div
                    ref={keypadRef}
                    className="min-h-[200px] min-w-[300px]"
                />
            </div>
        </>
    )
}