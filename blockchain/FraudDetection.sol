// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

/**
 * @title FraudDetection
 * @dev Store & retrieve ethereum accounts in a variable
 */
contract FraudDetection {

    struct Account{
        string account;
        string accountType;
        bool malicious;
        bool isExists;
    }
    mapping(string  => Account) private accountStore;

    event AddressCreate(address account, string accountType, bool malicious);
    event RejectCreate(address account, string accountType, string message);

    function store(string memory account, string memory accountType, bool malicious) public {
        if(accountStore[account].isExists) {
            emit RejectCreate(msg.sender, account, "Account with this address already exists.");
            return;
            }
        accountStore[account] = Account(account, accountType, malicious, true);
        emit AddressCreate(msg.sender, account, malicious);
    }

    function retrieve(string memory account) public view returns (string memory, string memory, bool){
        return (accountStore[account].account, accountStore[account].accountType, accountStore[account].malicious);
    }
}