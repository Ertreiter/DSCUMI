const { Wallet } = require("zksync-ethers");
const { Deployer } = require("@matterlabs/hardhat-zksync-deploy");
const hre = require("hardhat");

async function main() {
  console.log("📦 Deploying FingerprintRegistry...");

  // Load wallet
  const wallet = new Wallet(process.env.PRIVATE_KEY);

  // Initialize deployer
  const deployer = new Deployer(hre, wallet);

  // Load contract artifact
  const artifact = await deployer.loadArtifact("FingerprintRegistry");

  // Deploy contract
  const registry = await deployer.deploy(artifact);

  console.log("✅ Deployed at:", await registry.getAddress());
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});
