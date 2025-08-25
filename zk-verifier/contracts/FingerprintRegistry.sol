// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;   // 🔥 was 0.8.30


contract FingerprintRegistry {
    mapping(bytes32 => address) public fingerprintToWallet;
    event FingerprintRegistered(bytes32 indexed fingerprintHash, address indexed wallet);

    function registerFingerprint(bytes32 fingerprintHash) external {
        require(fingerprintToWallet[fingerprintHash] == address(0), "Already registered");
        fingerprintToWallet[fingerprintHash] = msg.sender;
        emit FingerprintRegistered(fingerprintHash, msg.sender);
    }

    function verifyFingerprint(bytes32 fingerprintHash) external view returns (bool) {
        return fingerprintToWallet[fingerprintHash] != address(0);
    }
}
