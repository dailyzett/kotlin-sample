async function importPublicKey(publicKeyBase64: string): Promise<CryptoKey> {
    // base64 디코딩
    const binaryString = atob(publicKeyBase64);
    const bytes = new Uint8Array(binaryString.length);
    for (let i = 0; i < binaryString.length; i++) {
        bytes[i] = binaryString.charCodeAt(i);
    }

    const publicKey = await window.crypto.subtle.importKey(
        'spki',
        bytes,
        {
            name: 'RSA-OAEP',
            hash: 'SHA-256'
        },
        true,
        ['encrypt']
    );
    return publicKey;
}

export async function encryptWithPublicKey(value: string, publicKeyBase64: string): Promise<string> {
    try {
        const publicKey = await importPublicKey(publicKeyBase64);
        const encoder = new TextEncoder();
        const data = encoder.encode(value);

        const encryptedData = await window.crypto.subtle.encrypt(
            {
                name: 'RSA-OAEP',
            },
            publicKey,
            data
        );

        const encryptedArray = new Uint8Array(encryptedData);
        let binaryString = '';
        encryptedArray.forEach(byte => {
            binaryString += String.fromCharCode(byte);
        });
        return btoa(binaryString);

    } catch (error) {
        console.error('Encryption failed:', error);
        throw error;
    }
}