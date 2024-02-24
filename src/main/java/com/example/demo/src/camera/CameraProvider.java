package com.example.demo.src.camera;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.camera.model.*;
import com.example.demo.src.camera.CameraDao;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

@Service

public class CameraProvider {
    private final CameraDao cameraDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public CameraProvider(CameraDao cameraDao) {
        this.cameraDao = cameraDao;
    }

//    public feedbackRes(GetCameraReq getCameraReq) throws BaseException{
//
//
//    }
//    public FeedbackRes feedbackRes(int userIdx) throws BaseException {
//        try {
//            GetUserRes getUserRes = userDao.getUser(userIdx);
//            return getUserRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    public GetCameraRes cosineSimilarity(GetCameraReq getCameraReq) throws BaseException {
        List<Float> vReliabilityList = new ArrayList<>();
        List<List<Float>> vectorsList = new ArrayList<>();
        List<Integer> pOrderList = new ArrayList<>();
        List<List<Float>> vReliability = new ArrayList<>();
        List<List<List<Float>>> PoseDataReq = new ArrayList<>();


        for (GetCameraReq.PoseList poseList : getCameraReq.getPoseList()) {
            List<GetCameraReq.PoseData> poseDataList = poseList.getPose();
            Integer pOrder = poseList.getP_order();
            pOrderList.add(pOrder);
            System.out.println("p_order : " + pOrderList);

            for (GetCameraReq.PoseData poseData : poseDataList) {
                int scaleX = getCameraReq.getScaleX();
                int scaleY = getCameraReq.getScaleY();
                float x = poseData.getX();
                float y = poseData.getY();
                float z = 1;

                // Create a list containing [x, y, z] and add it to vectorsList
                List<Float> vector = Arrays.asList(x/scaleX, y/scaleY, z);
                vectorsList.add(vector);

                float reliability = poseData.getReliability();
                vReliabilityList.add(reliability);

            }
            List<List<Float>> vectorsList_2 = new ArrayList<>(vectorsList);  // 새로운 객체로 복사
            List<Float> vReliability_2 = new ArrayList<>(vReliabilityList);
            vectorsList.clear();  // 원본 리스트 지우더라도 vectorsList_2는 영향을 받지 않음
            vReliabilityList.clear();
            PoseDataReq.add(vectorsList_2);
            vReliability.add(vReliability_2);

        }
        System.out.println("vector List : " + PoseDataReq);
        System.out.println("reliability List : " + vReliability);

        try {
            int poseIdx = getCameraReq.getPoseIdx();
            GetPoseNumRes getPoseNumRes = cameraDao.getPoseNum(getCameraReq);
            int poseNum = getPoseNumRes.getCnt();
            List<Integer> pOder = new ArrayList<>(Collections.nCopies(poseNum, 0));
            List<Double> Cdmax = new ArrayList<>(Collections.nCopies(poseNum, 0.0));
            List<Double> Cdavg = new ArrayList<>(Collections.nCopies(poseNum, 0.0));


            List<Float> targetList = new ArrayList<>();
            List<List<Float>> targetsList = new ArrayList<>();
            List<List<List<Float>>> allTargetList = new ArrayList<>();
            List<Float> tReliabilityList = new ArrayList<>();
            List<List<Float>> tReliability = new ArrayList<>();


            for(int i = 1; i <= poseNum; i++){
                GetTargetRes getTargetRes = cameraDao.getTarget(getCameraReq, i);
                //System.out.println(getTargetRes);
                List<GetTargetRes.PoseData> poseTargetList = getTargetRes.getPose();
                for (GetTargetRes.PoseData poseData : poseTargetList) {
                    float x = poseData.getX();
                    float y = poseData.getY();
                    float z = poseData.getZ();
                    targetList.add(x);
                    targetList.add(y);
                    targetList.add(z);

                    List<Float> vector = Arrays.asList(x, y, z);
                    targetsList.add(vector);
                }
                for (GetTargetRes.PoseData poseData : poseTargetList) {
                    float reliability = poseData.getReliability();
                    tReliabilityList.add(reliability);
                }

                List<List<Float>> targetsList_2 = new ArrayList<>(targetsList);
                targetsList.clear();
                allTargetList.add(targetsList_2);

                List<Float> tReliabilityList_2 = new ArrayList<>(tReliabilityList);
                tReliabilityList.clear();
                tReliability.add(tReliabilityList_2);


            }

            // 논문에서 원하는 target vector
            System.out.println("target vector : " + allTargetList);
            System.out.println("target reliability : " + tReliability);

            //여기에 cosine similarity 연산하는 것 구현하기 - target 이 데이터베이스 안에 들어있다고 가정
            //vector_v3안을 돌면서 target vector 와의 cosine similarity 를 구한 후 max 값과 그때의 인덱스(p_order)값 구하기

            // max
            for(int k = 0; k < poseNum; k++){
                tReliabilityList = tReliability.get(k);
                targetsList = allTargetList.get(k);
                double max = 0;
                int order = 1;

                for(int j = 0; j < PoseDataReq.size(); j++){
                    vReliabilityList = vReliability.get(j);
                    vectorsList = PoseDataReq.get(j);
                    double result_max = calculateCDmax(vectorsList,targetsList, vReliabilityList, tReliabilityList);
                    if(j == 0){
                        max = result_max;
                    }
                    else {
                        if (max < result_max) {
                            max = result_max;
                            int oderIdx = j;
                            System.out.println("pOder : " + pOrderList);
                            order = pOrderList.get(oderIdx);
                        }
                    }
                    System.out.println("max : " + max);
                    Cdmax.set(k,max);
                    pOder.set(k,order);
                }

                System.out.println("result_CDmax : " + Cdmax);
                System.out.println("pOder : " + pOrderList);
                System.out.println("pOder : " + pOder);

                System.out.println("1");

            }


            // 여기서 정의한 porder로 각 점마다 cosine similarity 비교하

            // avg
            for(int k = 0; k < poseNum; k++){
                tReliabilityList = tReliability.get(k);
                targetsList = allTargetList.get(k);
                double max = 0;

                for(int j = 0; j < PoseDataReq.size(); j++){
                    vReliabilityList = vReliability.get(j);
                    vectorsList = PoseDataReq.get(j);
                    double result_avg = calculateCDavg(vectorsList,targetsList, vReliabilityList, tReliabilityList);
                    if(j == 0){
                        max = result_avg;
                    }
                    else {
                        if (max < result_avg) {
                            max = result_avg;
                        }
                    }
                    System.out.println("max : " + max);
                    Cdavg.set(k,max);
                }
                System.out.println("result_CDavg : " + Cdavg);

            }

            GetCameraRes cameraRes = new GetCameraRes();
            cameraRes.setCameraResult(new ArrayList<>());
            for (int i = 0; i < poseNum; i ++){
                GetCameraRes.cameraResult cameraResult = new GetCameraRes.cameraResult();

                cameraResult.setPoseSeq(i+1);

                //CDmax 값 중 가장 최대가 되는 값 반환하기
                //pOder : 가장 유사도가 높은 점 반환
                //CDavg 값 중 가장 최대가 되는 값 반환하기
                cameraResult.setPOder(pOder.get(i));
                cameraResult.setCDmax(Cdmax.get(i));
                cameraResult.setCDavg(Cdavg.get(i));

                cameraRes.getCameraResult().add(cameraResult);
            }



            return cameraRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private static float calculateMagnitude(List<Float> vector) {
        return (float) Math.sqrt(vector.stream().mapToDouble(v -> v * v).sum());
    }


    public static float dotProduct(List<Float> vectorA, List<Float> vectorB) {
        // Ensure both vectors have the same size
        if (vectorA.size() != vectorB.size()) {
            throw new IllegalArgumentException("Vectors must have the same size");
        }

        // Calculate dot product
        float dotProduct = 0;
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
        }

        return dotProduct;
    }

    private static double cosineSimilarity(List<Float> v, List<Float> t){
        double cosineSimilarity;
        float dotProduct = dotProduct(v, t);
        float magnitudeV = calculateMagnitude(v);
        float magnitudeT = calculateMagnitude(t);

            if (magnitudeV == 0 || magnitudeT == 0) {
                cosineSimilarity = 0;
            } else {
                cosineSimilarity = dotProduct / (magnitudeV * magnitudeT);
            }

        return cosineSimilarity;
    }

    private static double calculateCDmax(List<List<Float>> v, List<List<Float>> t, List<Float> vr, List<Float> tr){
        double result_max = Math.max(vr.get(0), tr.get(0));
        for (int i = 1; i < v.size(); i++) {
            float maxAtIndex = Math.max(vr.get(i), tr.get(i));
            result_max += maxAtIndex;
        }

        result_max = 1/result_max;
        double sum = 0;

        for (int i = 0; i < t.size(); i++) {
            float dotProduct = dotProduct(v.get(i), t.get(i));
            float magnitudeV = calculateMagnitude(v.get(i));
            float magnitudeT = calculateMagnitude(t.get(i));

            double cosineSimilarity;
            if (magnitudeV == 0 || magnitudeT == 0) {
                cosineSimilarity = 0;
            } else {
                cosineSimilarity = dotProduct / (magnitudeV * magnitudeT);
            }

            float max = vr.get(i);

            if(vr.get(i) < tr.get(i)){
                max = tr.get(i);
            }
            sum += max * cosineSimilarity;
        }
        result_max = result_max * sum;
        return result_max;
    }

    private static double calculateCDavg(List<List<Float>> v, List<List<Float>> t, List<Float> vr, List<Float> tr){
        // CD_avg
        double result_avg = (vr.get(0)+tr.get(0))/2;

        for (int i = 1; i < vr.size(); i++) {
            float avgAtIndex = (vr.get(i) + tr.get(i))/2;
            result_avg += avgAtIndex;
        }

        result_avg = 1/result_avg;
        double sum_avg = 0;

        for (int i = 0; i < t.size(); i++) {
            float dotProduct = dotProduct(v.get(i), t.get(i));
            float magnitudeV = calculateMagnitude(v.get(i));
            float magnitudeT = calculateMagnitude(t.get(i));

            double cosineSimilarity;
            if (magnitudeV == 0 || magnitudeT == 0) {
                cosineSimilarity = 0;
            } else {
                cosineSimilarity = dotProduct / (magnitudeV * magnitudeT);
            }

            float avg = (vr.get(i) + tr.get(i))/2;
            sum_avg += avg * cosineSimilarity;
        }

        result_avg = result_avg * sum_avg;
        System.out.println("result_CDavg : " + result_avg);
        return result_avg;
    }


}


