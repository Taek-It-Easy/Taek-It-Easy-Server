package com.example.demo.src.camera;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.camera.model.*;
import com.example.demo.src.camera.CameraDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    private static float calculateMagnitude(List<Float> vector) {
        return (float) Math.sqrt(vector.stream().mapToDouble(v -> v * v).sum());
    }

    public GetCameraRes cosineSimilarity(GetCameraReq getCameraReq) throws BaseException {
        List<GetCameraReq.PoseData> poseDataList = getCameraReq.getPose();
        List<Float> vectorList = new ArrayList<>();
        List<Float> vReliabilityList = new ArrayList<>();
        List<List<Float>> vectorsList = new ArrayList<>();
        //int chapterIdx = GetCameraReq.getChapterIdx();
        for (GetCameraReq.PoseData poseData : poseDataList) {
            float x = poseData.getX();
            float y = poseData.getY();
            float z = poseData.getZ();
            vectorList.add(x);
            vectorList.add(y);
            vectorList.add(z);

            List<Float> vector = Arrays.asList(x, y, z);
            vectorsList.add(vector);

        }
        for (GetCameraReq.PoseData poseData : poseDataList) {
            float reliability = poseData.getReliability();
            vReliabilityList.add(reliability);
        }
        // 논문에서 원하는 vector
        System.out.println("vector : " + vectorList);
        // 혹시나 싶어서 하나 더 만든 vectorsList
        System.out.println("vector_v2 : " + vectorsList);
        //System.out.println(vectorsList);
        System.out.println("vector reliability : " + vReliabilityList);

        try {
            GetTargetRes getTargetRes = cameraDao.getTarget(getCameraReq);
            int chapterIdx = getCameraReq.getChapterIdx();

            List<GetTargetRes.PoseData> poseTargetList = getTargetRes.getPose();
            List<Float> targetList = new ArrayList<>();
            List<Float> treliabilityList = new ArrayList<>();
            List<List<Float>> targetsList = new ArrayList<>();
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
                treliabilityList.add(reliability);
            }
            // 논문에서 원하는 target vector
            System.out.println("target vector : " + targetList);
            // 혹시나 싶어서 하나 더 만든 targetsList
            System.out.println("target vector_v2 : " + targetsList);
            System.out.println("target reliability : " + treliabilityList);

            //여기에 cosine simularity 연산하는 것 구현하기 - target이 데이터베이스 안에 들어있다고 가정

            // 첫번째 값의 최댓값 찾기
            double result_max = Math.max(vReliabilityList.get(0), treliabilityList.get(0));
            for (int i = 1; i < vReliabilityList.size(); i++) {
                float maxAtIndex = Math.max(vReliabilityList.get(i), treliabilityList.get(i));
                result_max += maxAtIndex;
            }

            result_max = 1/result_max;
            double sum = 0;

            for (int i = 0; i < targetsList.size(); i++) {
                float dotProduct = dotProduct(vectorsList.get(i), targetsList.get(i));
                float magnitudeV = calculateMagnitude(vectorsList.get(i));
                float magnitudeT = calculateMagnitude(targetsList.get(i));

                double cosineSimilarity;
                if (magnitudeV == 0 || magnitudeT == 0) {
                    cosineSimilarity = 0;
                } else {
                    cosineSimilarity = dotProduct / (magnitudeV * magnitudeT);
                }

                float max = vReliabilityList.get(i);

                if(vReliabilityList.get(i) < treliabilityList.get(i)){
                    max = treliabilityList.get(i);
                }

                sum += max * cosineSimilarity;
            }

            result_max = result_max * sum;
            System.out.println("result_CDmax : " + result_max);

            // CD_avg
            double result_avg = (vReliabilityList.get(0)+treliabilityList.get(0))/2;

            for (int i = 1; i < vReliabilityList.size(); i++) {
                float avgAtIndex = (vReliabilityList.get(i) + treliabilityList.get(i))/2;
                result_avg += avgAtIndex;
            }

            result_avg = 1/result_avg;
            double sum_avg = 0;

            for (int i = 0; i < targetsList.size(); i++) {
                float dotProduct = dotProduct(vectorsList.get(i), targetsList.get(i));
                float magnitudeV = calculateMagnitude(vectorsList.get(i));
                float magnitudeT = calculateMagnitude(targetsList.get(i));

                double cosineSimilarity;
                if (magnitudeV == 0 || magnitudeT == 0) {
                    cosineSimilarity = 0;
                } else {
                    cosineSimilarity = dotProduct / (magnitudeV * magnitudeT);
                }

                float avg = (vReliabilityList.get(i) + treliabilityList.get(i))/2;
                sum_avg += avg * cosineSimilarity;
            }

            result_avg = result_avg * sum_avg;
            System.out.println("result_CDavg : " + result_avg);

            GetCameraRes cameraRes = new GetCameraRes();
            cameraRes.setChapterIdx(chapterIdx);
            cameraRes.setCDmax(result_max);
            cameraRes.setCDavg(result_avg);


            return cameraRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
