//SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract Statements {

    struct Request {
        uint256 from;
        uint256 to;
        bool executed;
        bytes cid;
    }

    event StatementRequest(address requestor, uint256 index);

    mapping(address => Request[]) public requests;

    function requestStatement(uint256 from, uint256 to) public payable {
        require(from < to, "'from' timestamp is greater then 'to' timestamp");
        // todo: calculate payment for fee
        require(msg.value >= 1 ether, "payment is too low");

        uint256 index = requests[msg.sender].length;

        Request memory request;
        request.from = from;
        request.to = to;
        request.executed = false;
        requests[msg.sender].push(request);

        emit StatementRequest(msg.sender, index);
    }
}
