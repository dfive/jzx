package pt.ist.ave.jzx.instructions;

import java.util.HashMap;


public class JRNZ_D extends Instruction {

	private int _tstates;
	
	private static HashMap<Integer, Integer> addsCache = new HashMap<Integer, Integer>();
	
	private int getAdd(){
		int m_pc16 = _cpu.getM_pc16();
		if(!addsCache.containsKey(m_pc16)){
			addsCache.put(m_pc16, _cpu.add16(m_pc16, (byte) readNextMemoryPosition8() + 1));
		}
		return addsCache.get(m_pc16);
	}
	
	public JRNZ_D(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		if (!_cpu.getM_zeroF()) {
			_tstates = 12;
			_cpu.setM_pc16(getAdd());
		} else {
			_tstates = 7;
			_cpu.inc16pc();
		}
	}

	@Override
	public int incTstates() {
		return _tstates;
	}

}
