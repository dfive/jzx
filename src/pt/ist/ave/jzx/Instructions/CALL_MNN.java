package pt.ist.ave.jzx.Instructions;

public class CALL_MNN extends Instruction {

	public CALL_MNN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		if (m_signF) {
//			push(incinc16(m_pc16));
//			m_pc16 = m_memory.read16(m_pc16);
//		} else {
//			m_pc16 = incinc16(m_pc16);
//		}
		if (_cpu.getM_signF()) {
			_cpu.push(_cpu.incinc16(_cpu.getM_pc16()));
			_cpu.setM_pc16(_cpu.getM_memory().read16(_cpu.getM_pc16()));
		} else {
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
		}
	}

	@Override
	public int incTstates() {
		if(_cpu.getM_signF()){
//			m_tstates += 17;
			return 17; 
		}else{
//			m_tstates += 10;
			return 10;
		}
	}

}
