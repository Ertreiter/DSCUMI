require("dotenv").config();  // 👈 must be first
require("@matterlabs/hardhat-zksync-solc");
require("@matterlabs/hardhat-zksync-ethers");
require("@matterlabs/hardhat-zksync-verify");

const PRIVATE_KEY = process.env.PRIVATE_KEY;

if (!PRIVATE_KEY) {
  console.error("❌ No PRIVATE_KEY found in .env");
}

module.exports = {
  zksolc: {
    version: "1.4.0",
    compilerSource: "binary",
    settings: {},
  },
  solidity: {
    version: "0.8.24",
  },
  networks: {
    zkSyncSepolia: {
      url: "https://sepolia.era.zksync.dev",
      ethNetwork: "sepolia",
      zksync: true,
      accounts: [PRIVATE_KEY], // ✅ must be a string
    },
  },
};
