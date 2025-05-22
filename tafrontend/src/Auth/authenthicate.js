import { AuthenticationDetails, CognitoUser, CognitoUserAttribute } from 'amazon-cognito-identity-js';
import userpool from '../userpool';

export const authenticate = (email, password) => {
    return new Promise((resolve, reject) => {
        const user = new CognitoUser({
            Username: email,
            Pool: userpool
        });

        const authDetails = new AuthenticationDetails({
            Username: email,
            Password: password
        });

        user.authenticateUser(authDetails, {
            onSuccess: (result) => {
                console.log("Login successful");

                // Fetch user attributes
                user.getUserAttributes((err, attributes) => {
                    if (err) {
                        console.error("Failed to get user attributes", err);
                        reject(err);
                    } else {
                        const emailAttr = attributes.find(attr => attr.getName() === 'email');
                        const subAttr = attributes.find(attr => attr.getName() === 'sub');

                        if (emailAttr && subAttr) {
                            const userEmail = emailAttr.getValue();
                            const userId = subAttr.getValue();

                            sessionStorage.setItem('username', userEmail);
                            sessionStorage.setItem('userId', userId);
                        }

                        resolve(result);
                    }
                });
            },

            onFailure: (err) => {
                console.log("Login failed", err);
                reject(err);
            }
        });
    });
};

export const confirmSignUp = (email, confirmationCode) => {
    return new Promise((resolve, reject) => {
        const user = new CognitoUser({
            Username: email,
            Pool: userpool
        });

        user.confirmRegistration(confirmationCode, true, (err, result) => {
            if (err) {
                console.log("Confirmation failed", err);
                reject(err);
            } else {
                console.log("Confirmation successful", result);
                resolve(result);
            }
        });
    });
};

export const logout = () => {
    const user = userpool.getCurrentUser();
    if (user) {
        user.signOut();
    }

    sessionStorage.removeItem('username');
    sessionStorage.removeItem('userId');

    window.location.href = '/';
};
