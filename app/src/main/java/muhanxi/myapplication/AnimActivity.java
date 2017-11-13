package muhanxi.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;




/**
 * https://github.com/REBOOTERS/AndroidAnimationExercise
 */
public class AnimActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
         screenHeight = wm.getDefaultDisplay().getHeight();

        imageView = (ImageView) findViewById(R.id.image_anim);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println("v = " + v.getX() + "  " + v.getY());



            }
        });


        findViewById(R.id.exe_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadxml1();
            }
        });
    }

    //对象动画
    public void  rotateAnim(View view){
        /**
         * view 需要执行动画的view
         * rotationY view 所具有的属性
         * 0,360f 可变参数  0度旋转到360度
         */
        ObjectAnimator.ofFloat(view,"rotationY",0,360f)
                .setDuration(3000)
                .start();


    }

    public void  rotateAnim1(final View view){


        ObjectAnimator objectAnimator =   new ObjectAnimator()
                .ofFloat(view,"zzz",1.0f,0.0f)
                .setDuration(500) ;
        objectAnimator.start();

        //动画执行过程中的监听
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                System.out.println("animation = " + animation.getAnimatedValue());

                float val = (float) animation.getAnimatedValue() ;


                view.setAlpha(val);

                view.setScaleX(val);

                view.setScaleY(val);
            }
        });


    }


    private void propertyValues(View view){




        PropertyValuesHolder propertyValuesHolderalpha = PropertyValuesHolder.ofFloat("alpha",1.0f,0.0f,1.0f) ;
        PropertyValuesHolder propertyValuesHolderalScaleX = PropertyValuesHolder.ofFloat("scaleX",1.0f,0.0f,1.0f) ;
        PropertyValuesHolder propertyValuesHolderalScaleY = PropertyValuesHolder.ofFloat("scaleY",1.0f,0.0f,1.0f) ;

        ObjectAnimator.ofPropertyValuesHolder(view,propertyValuesHolderalpha,propertyValuesHolderalScaleX,propertyValuesHolderalScaleY).setDuration(500).start();

//        ObjectAnimator anim = new ObjectAnimator();
//        anim.setTarget(view);
//        anim.setValues(propertyValuesHolderalScaleX,propertyValuesHolderalpha,propertyValuesHolderalScaleY);
//        anim.setDuration(300);
//        anim.start();

    }

    float screenHeight = 1000 ;

    //值动画
    public void ValueAnimator1(View view){

        //构造一个 ValueAnimator 对象
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,600);

        //为 ValueAnimator 设置需要执行动画的view
        valueAnimator.setTarget(imageView);

        valueAnimator.setDuration(1000).start();

        //执行过程中的监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                System.out.println("animation = " + animation.getAnimatedValue());
                //通过 setTranslationY 改变view 的位置
                imageView.setTranslationY((Float) animation.getAnimatedValue());

            }
        });

    }



//    值动画
    public void ValueAnimator2(View view) {


        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0,0));
//        http://doc.okbase.net/qiujuer/archive/122469.html
//        插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
//        计算属性值的，即可计算任意类型的值。//估值器
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
        {
            // fraction = t / duration
            //fraction 当前运行的时间比上总持续时间的 比值(中间经过插入器的规则运算)
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue)
            {
                System.out.println("fraction = " + fraction);
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                imageView.setX(point.x);
                imageView.setY(point.y);

            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }



    public void animatorSet1(View view){

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"scaleX",1.0f,2.0f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view,"scaleY",1.0f,2.0f);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(2000);
        animationSet.setInterpolator(new LinearInterpolator());
//        playTogether 多个动画一起执行
        animationSet.playTogether(objectAnimator, objectAnimator1);
        animationSet.start();

    }


    public void animatorSet2(View view){

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"scaleX",1.0f,2.0f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view,"scaleY",1.0f,2.0f);

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view,"x",view.getX(),200f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(view,"y",view.getY(),200f);

        AnimatorSet animatorSet = new AnimatorSet();
//**
//         * anim1，anim2,anim3同时执行
//                * anim4接着执行
//                */
        animatorSet.play(objectAnimator).with(objectAnimator1);
        animatorSet.play(objectAnimator1).with(objectAnimator2);


        //objectAnimator3 执行完 在执行objectAnimator2
        animatorSet.play(objectAnimator2).after(objectAnimator3);
        animatorSet.setDuration(1000);
        animatorSet.start();

    }



    // load xml

    public void loadxml1(){



      Animator animator =    AnimatorInflater.loadAnimator(this,R.animator.animator);

        animator.setTarget(imageView);

        animator.start();

    }

    //默认 以中心点 缩放
    public void loadxml2(){

        Animator animator = AnimatorInflater.loadAnimator(this,R.animator.animator1);

        // 修改缩放中心位置
        imageView.setPivotX(0);
        imageView.setPivotY(0);
        imageView.invalidate();



        animator.setTarget(imageView);


        animator.start();

    }


    // TranslateAnimation animation
    public void TranslateAnimation(){
        TranslateAnimation animation = new TranslateAnimation(0,750,0,0);
        animation.setDuration(2000);
        animation.setRepeatCount(1);

        imageView.setAnimation(animation);

        animation.startNow();

    }

    Animation animation ;

    public void alpha(View view){

        animation =   AnimationUtils.loadAnimation(this,R.anim.alpha);

        imageView.startAnimation(animation);
    }


    public void rotate(View view){

        animation =  AnimationUtils.loadAnimation(this,R.anim.rotate);

        imageView.startAnimation(animation);
    }

    public void scale(View view){

        animation = AnimationUtils.loadAnimation(this,R.anim.scale);
        imageView.startAnimation(animation);

    }

    public void translate(View view){

        animation = AnimationUtils.loadAnimation(this,R.anim.translate);

        imageView.startAnimation(animation);
    }


}
