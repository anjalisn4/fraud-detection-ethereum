import numpy as np
import pickle
from subprocess import Popen, PIPE, STDOUT
import os,sys

eoa_dt = pickle.load(open('../code/exec-results/eoa/models/dt.pkl', 'rb'))
eoa_knn = pickle.load(open('../code/exec-results/eoa/models/knn.pkl', 'rb'))
eoa_rf = pickle.load(open('../code/exec-results/eoa/models/rf.pkl', 'rb'))
eoa_xgb = pickle.load(open('../code/exec-results/eoa/models/xgb.pkl', 'rb'))

# Smart Contract models
sc_dt = pickle.load(open('../code/exec-results/sc/models/dt.pkl', 'rb'))
sc_knn = pickle.load(open('../code/exec-results/sc/models/knn.pkl', 'rb'))
sc_rf = pickle.load(open('../code/exec-results/sc/models/rf.pkl', 'rb'))
sc_xgb = pickle.load(open('../code/exec-results/sc/models/xgb.pkl', 'rb'))


def pred(address):
    result = {
        'Account Type':'Not Valid',
        'Decision Tree Classifier Prediction':'NA',
        'KNN Prediction': 'NA',
        'Random Forest Classifier': 'NA',
        'XGBoost Classifier': 'NA'}
    try:
        p = Popen(['java', '-jar', '../code/exec-results/etherscan.jar',
                address], stdout=PIPE, stderr=STDOUT)
        l = ""
        for line in p.stdout:
            l = line
        l = l.strip().decode("utf-8")
        x = l.split(",")
        if(len(x)==19 and x[18] == 'Smart Contract'):
            feat = getSmartContractFeatures(x)
            prediction = sc_dt.predict(feat)[0]
            result['Account Type']= x[18]
            result['Decision Tree Classifier Prediction']= "Felonious" if prediction == 1 else 'Non-Felonious'
            prediction = sc_knn.predict(feat)[0]
            result['KNN Prediction']= "Felonious" if prediction == 1 else 'Non-Felonious'
            prediction = sc_rf.predict(feat)[0]
            result['Random Forest Classifier']= "Felonious" if prediction == 1 else 'Non-Felonious'
            prediction = sc_xgb.predict(feat)[0]
            result['XGBoost Classifier']= "Felonious" if prediction == 1 else 'Non-Felonious'
            return result
        elif(len(x)==45 and x[44] == 'EOA'):
            feat = getEOAFeatures(x)
            prediction = eoa_dt.predict(feat)[0]
            result['Account Type']= x[44]
            result['Decision Tree Classifier Prediction']= "Felonious" if prediction == 1 else 'Non-Felonious'
            prediction = eoa_knn.predict(feat)[0]
            result['KNN Prediction']= "Felonious" if prediction == 1 else 'Non-Felonious'
            prediction = eoa_rf.predict(feat)[0]
            result['Random Forest Classifier']= "Felonious" if prediction == 1 else 'Non-Felonious'
            prediction = eoa_xgb.predict(feat)[0]
            result['XGBoost Classifier']= "Felonious" if prediction == 1 else 'Non-Felonious'
            return result
        return result
    except Exception as e:
        exc_type, exc_obj, exc_tb = sys.exc_info()
        fname = os.path.split(exc_tb.tb_frame.f_code.co_filename)[1]
        print(exc_type, fname, exc_tb.tb_lineno)
        result['Account Type']= exc_type
        return result

def getSmartContractFeatures(tx):

    f1_contract_creation_time = tx[0]
    f5_first_contract_invoke_time = tx[4]
    f6_last_contract_invoke_time = tx[5]
    f4_gas_price_contract_creation = tx[3]
    f12_avg_gas_price_contract_invocations = tx[11]
    f2_transaction_fee_spent_contract_creation = tx[1]
    f11_total_gas_price_contract_invocations = tx[10]
    f18_avg_gas_used_contract_invocations = tx[17]
    f10_avg_gas_used_contract_invocations = tx[9]
    f17_total_gas_used_contract_invocations = tx[16]

    return [np.array([
        f1_contract_creation_time,
        f5_first_contract_invoke_time,
        f6_last_contract_invoke_time,
        f4_gas_price_contract_creation,
        f12_avg_gas_price_contract_invocations,
        f2_transaction_fee_spent_contract_creation,
        f11_total_gas_price_contract_invocations,
        f18_avg_gas_used_contract_invocations,
        f10_avg_gas_used_contract_invocations,
        f17_total_gas_used_contract_invocations],dtype=float)]


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
    f5_value_difference = tx[4]

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
        f5_value_difference],dtype=float)]
