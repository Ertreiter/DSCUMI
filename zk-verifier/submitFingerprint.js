const { Wallet, Provider, Contract } = require("zksync-web3");
const { ethers } = require("ethers");
require("dotenv").config();

const PRIVATE_KEY = process.env.WALLET_PRIVATE_KEY;
const CONTRACT_ADDRESS = process.env.CONTRACT_ADDRESS;
const provider = new Provider("https://sepolia.era.zksync.dev");
const wallet = new Wallet(PRIVATE_KEY, provider);

const abi = [
  "function registerFingerprint(bytes32 fingerprintHash) external",
  "function verifyFingerprint(bytes32 fingerprintHash) external view returns (bool)"
];

async function main() {
  const args = process.argv.slice(2);
  if (args.length === 0) {
    console.error("❌ No fingerprint hash provided");
    process.exit(1);
  }
  const fingerprintHash = args[0];
  const contract = new Contract(CONTRACT_ADDRESS, abi, wallet);

  console.log("🧬 Submitting fingerprint hash:", fingerprintHash);
  const tx = await contract.registerFingerprint(fingerprintHash);
  await tx.wait();
  console.log("✅ Stored on zkSync Era:", tx.hash);
}

main();
