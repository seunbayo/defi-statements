import "@nomiclabs/hardhat-ethers"
import "@nomiclabs/hardhat-waffle"

import { expect } from "chai";
import { ethers } from "hardhat";
import { Statements } from "../typechain";

describe("Statements", async () => {
  let statements: Statements;

  beforeEach(async () => {
    const Statements = await ethers.getContractFactory("Statements");
    const localStatements = await Statements.deploy() as Statements;
    statements = await localStatements.deployed();
  });

  it("should persist statement request", async () => {
    // given
    const from = 0;
    const to = 1000;

    // when
    const transaction = await statements.requestStatement(from, to,
      { value: ethers.utils.parseEther("1") });

    // then
    const account = (await ethers.getSigners())[0];
    expect(transaction).to.emit(statements, 'StatementRequest')
      .withArgs(account.address, 0);

    const request = await statements.requests(account.address, 0);
    expect(request.from).to.be.equal(from);
    expect(request.to).to.be.equal(to);
    expect(request.executed).to.be.false;
    expect(request.cid).to.be.equal("0x");
  });

  it("should revert if 'from' is greater than 'to'", async () => {
    // given
    const from = 1000;
    const to = 0;

    // then
    await expect(statements.requestStatement(from, to, { value: ethers.utils.parseEther("1") }))
      .revertedWith("'from' timestamp is greater then 'to' timestamp");
  });

  it("should revert if provided payment is too low", async () => {
    // given
    const from = 0;
    const to = 1000;

    // then
    await expect(statements.requestStatement(from, to, { value: ethers.utils.parseEther("0.5") }))
      .revertedWith("payment is too low");
  });
});
