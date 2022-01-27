import numpy as np
import pickle
from subprocess import Popen, PIPE, STDOUT
import os,sys

eoa_model = pickle.load(open('../code/eoa_dt_model.pkl', 'rb'))
sc_model = pickle.load(open('../code/sc_dt_model.pkl', 'rb'))


def pred(address):
    try:
        p = Popen(['java', '-jar', '/home/ubuntu/Desktop/etherscan.jar',
                address], stdout=PIPE, stderr=STDOUT)
        l = ""
        for line in p.stdout:
            l = line
        l = l.strip().decode("utf-8")
        x = l.split(",")
        if(len(x)==19 and x[18] == 'SmartContract'):
            feat = getSmartContractFeatures(x)
            prediction = sc_model.predict(feat)[0]
            return prediction, x[18]
        elif(len(x)==45 and x[44] == 'EOA'):
            feat = getEOAFeatures(x)
            prediction = eoa_model.predict(feat)[0]
            return prediction, x[44]
        else:
            return -2, 'Not Valid'
    except Exception as e:
        exc_type, exc_obj, exc_tb = sys.exc_info()
        fname = os.path.split(exc_tb.tb_frame.f_code.co_filename)[1]
        print(exc_type, fname, exc_tb.tb_lineno)
        return -1,'Not Valid'

def getSmartContractFeatures(tx):
    f5_first_contract_invoke_time = tx[4]
    f1_contract_creation_time = tx[0]
    f6_last_contract_invoke_time = tx[5]
    f4_gas_price_contract_creation = tx[3]
    f12_avg_gas_price_contract_invocations = tx[11]
    f2_transaction_fee_spent_contract_creation = tx[1]
    f18_avg_gas_used_contract_invocations = tx[17]
    f10_avg_gas_used_contract_invocations = tx[9]
    f11_total_gas_price_contract_invocations = tx[10]
    f7_active_duration = tx[6]

    return [np.array([
        f5_first_contract_invoke_time,
        f1_contract_creation_time,
        f6_last_contract_invoke_time,
        f4_gas_price_contract_creation,
        f12_avg_gas_price_contract_invocations,
        f2_transaction_fee_spent_contract_creation,
        f18_avg_gas_used_contract_invocations,
        f10_avg_gas_used_contract_invocations,
        f11_total_gas_price_contract_invocations,
        f7_active_duration])]


def getEOAFeatures(tx):
    f10_first_transaction_time = tx[9]
    f11_last_transaction_time = tx[10]
    f28_total_success_transactions = tx[27]
    f27_total_success_transactions_outgoing = tx[26]
    f22_average_outgoing_gas_price = tx[22]
    f26_total_success_transactions_incoming = tx[25]
    f19_outgoing_gas_price = tx[18]
    f44_transaction_fee_spent = tx[43]
    f36_standard_deviation_gas_price_outgoing = tx[35]
    f43_transaction_fee_spent_outgoing = tx[42]

    return [np.array([
        f10_first_transaction_time,
        f11_last_transaction_time,
        f28_total_success_transactions,
        f27_total_success_transactions_outgoing,
        f22_average_outgoing_gas_price,
        f26_total_success_transactions_incoming,
        f19_outgoing_gas_price,
        f44_transaction_fee_spent,
        f36_standard_deviation_gas_price_outgoing,
        f43_transaction_fee_spent_outgoing])]
