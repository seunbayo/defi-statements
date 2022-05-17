import { ethers } from "hardhat";

const deploy = async () => {
  const Statements = await ethers.getContractFactory("Statements");
  const statements = await Statements.deploy();

  await statements.deployed();

  console.log("Statements contract deployed to:", statements.address);
}

deploy()
  .catch((error) => {
    console.error(error);
    process.exitCode = 1;
  });
